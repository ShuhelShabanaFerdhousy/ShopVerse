package com.shopverse.order_service.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopverse.common.enums.PaymentMethod;
import com.shopverse.common.enums.PaymentStatus;
import com.shopverse.common.event.OrderCreatedEvent;
import com.shopverse.common.event.OrderDeliveredEvent;
import com.shopverse.common.event.PaymentResultEvent;
import com.shopverse.common.event.StockItem;
import com.shopverse.common.event.StockReductionEvent;
import com.shopverse.order_service.client.CartClient;
import com.shopverse.order_service.client.ProductClient;
import com.shopverse.order_service.client.UserClient;
import com.shopverse.order_service.dto.CartItemResponse;
import com.shopverse.order_service.dto.CartResponse;
import com.shopverse.order_service.dto.OrderRequest;
import com.shopverse.order_service.dto.OrderResponse;
import com.shopverse.order_service.dto.ProductResponse;
import com.shopverse.order_service.entity.Order;
import com.shopverse.order_service.entity.OrderItem;
import com.shopverse.order_service.enums.OrderStatus;
import com.shopverse.order_service.exception.EmptyOrderException;
import com.shopverse.order_service.exception.InsufficientStockException;
import com.shopverse.order_service.exception.InvalidOrderStatusException;
import com.shopverse.order_service.exception.OrderNotFoundException;
import com.shopverse.order_service.kafka.OrderCreatedProducer;
import com.shopverse.order_service.kafka.OrderDeliveredProducer;
import com.shopverse.order_service.kafka.StockReductionProducer;
import com.shopverse.order_service.mapper.OrderMapper;
import com.shopverse.order_service.repository.OrderRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;
    private final CartClient cartClient;
    private final OrderCreatedProducer orderCreatedProducer;
    private final OrderDeliveredProducer orderDeliveredProducer;
    private final StockReductionProducer stockReductionProducer;

    public OrderServiceImpl(OrderRepository orderRepository, ProductClient productClient, UserClient userClient, CartClient cartClient, OrderCreatedProducer orderCreatedProducer, OrderDeliveredProducer orderDeliveredProducer, StockReductionProducer stockReductionProducer) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.userClient = userClient;
        this.cartClient = cartClient;
        this.orderCreatedProducer = orderCreatedProducer;
        this.orderDeliveredProducer = orderDeliveredProducer;
        this.stockReductionProducer = stockReductionProducer;
    }
    
    private Order getOrderEntity(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("No order found with id: " + orderId));
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {
    	userClient.getUserById(request.getUserId());
    	CartResponse cart = cartClient.getCartByUserId(request.getUserId());
    	
    	if (cart.getItems() == null || cart.getItems().isEmpty()) {
    	    throw new EmptyOrderException("Cart is empty");
    	}
    	
    	Order order = new Order();
    	order.setUserId(request.getUserId());
    	order.setItems(new ArrayList<>());
    	order.setPaymentMethod(request.getPaymentMethod());
        
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemResponse item : cart.getItems()) {
        	ProductResponse product = productClient.getProductById(item.getProductId());
        	
        	if (item.getQuantity() > product.getStockQuantity()) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }
        	
        	OrderItem orderitem = new OrderItem();
        	orderitem.setProductId(item.getProductId());
        	orderitem.setProductName(product.getName());
        	orderitem.setPrice(product.getPrice());
        	orderitem.setQuantity(item.getQuantity());
        	
        	orderitem.setOrder(order);
        	order.getItems().add(orderitem);

            totalAmount = totalAmount.add(
                product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        order.setOrderStatus(OrderStatus.CREATED);
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.saveAndFlush(order);
        
        OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId(), savedOrder.getUserId(), savedOrder.getTotalAmount(), savedOrder.getPaymentMethod());
        orderCreatedProducer.sendOrderCreatedEvent(event);
        
        cartClient.clearCart(request.getUserId());
        return OrderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        Order order = getOrderEntity(orderId);
        return OrderMapper.toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUser(Long userId) {
    	List<Order> orders = orderRepository.findByUserId(userId);

        List<OrderResponse> response = new ArrayList<>();
        for (Order order : orders) {
            response.add(OrderMapper.toResponse(order));
        }

        return response;
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = getOrderEntity(orderId);
        if(order.getPaymentMethod() == PaymentMethod.COD) {
        	if (order.getOrderStatus() == OrderStatus.CREATED) {
                if (orderStatus != OrderStatus.SHIPPED && orderStatus != OrderStatus.CANCELLED) {
                    throw new InvalidOrderStatusException("Order must be SHIPPED or CANCELLED.");
                }
            } else if (order.getOrderStatus() == OrderStatus.SHIPPED) {
                if (orderStatus != OrderStatus.DELIVERED) {
                    throw new InvalidOrderStatusException("Order must be DELIVERED or CANCELLED.");
                }
            } else {
                throw new InvalidOrderStatusException("COD order status cannot be changed.");
            }
    	} else {
    		if (order.getOrderStatus() == OrderStatus.CREATED) {
                if (orderStatus != OrderStatus.PAID && orderStatus != OrderStatus.CANCELLED) {
                    throw new InvalidOrderStatusException("Order must be PAID or CANCELLED.");
                }
            } else if (order.getOrderStatus() == OrderStatus.PAID) {
                if (orderStatus != OrderStatus.SHIPPED) {
                    throw new InvalidOrderStatusException("Order must be SHIPPED or CANCELLED.");
                }
            } else if (order.getOrderStatus() == OrderStatus.SHIPPED) {
                if (orderStatus != OrderStatus.DELIVERED) {
                    throw new InvalidOrderStatusException("Order must be DELIVERED.");
                }
            } else {
                throw new InvalidOrderStatusException("Order status cannot be changed.");
            }
    	}

        order.setOrderStatus(orderStatus);
        Order savedOrder = orderRepository.save(order);

        if(savedOrder.getOrderStatus() == OrderStatus.SHIPPED && savedOrder.getPaymentMethod() == PaymentMethod.COD) {
        	List<StockItem> items = new ArrayList<>();
			for(OrderItem item: savedOrder.getItems()) {
				StockItem stockItem = new StockItem(item.getProductId(), item.getQuantity());
				items.add(stockItem);
			}
			StockReductionEvent stockEvent = new StockReductionEvent(order.getId(), items);
			stockReductionProducer.sendStockReductionEvent(stockEvent);
        }
        
        if(savedOrder.getOrderStatus() == OrderStatus.DELIVERED) {
        	OrderDeliveredEvent event = new OrderDeliveredEvent(savedOrder.getId());
        	orderDeliveredProducer.sendOrderDeliveredEvent(event);
        }
   
        return OrderMapper.toResponse(savedOrder);
    }
    
    @Override
    public void handlePaymentResult(PaymentResultEvent event) {
    	Order order = orderRepository.findById(event.getOrderId()).orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + event.getOrderId()));
		
		if(order.getOrderStatus() == OrderStatus.PAID) {
			return;
		}
		
		if(event.getPaymentStatus() == PaymentStatus.SUCCESS) {
			order.setOrderStatus(OrderStatus.PAID);
			
			List<StockItem> items = new ArrayList<>();
			for(OrderItem item: order.getItems()) {
				StockItem stockItem = new StockItem(item.getProductId(), item.getQuantity());
				items.add(stockItem);
			}
			StockReductionEvent stockEvent = new StockReductionEvent(order.getId(), items);
			stockReductionProducer.sendStockReductionEvent(stockEvent);
		} else if(event.getPaymentStatus() == PaymentStatus.FAILED) {
			order.setOrderStatus(OrderStatus.PAYMENT_FAILED);
		}
		
		orderRepository.save(order);
    }
}
