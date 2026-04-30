package com.shopverse.common.event;

public class OrderDeliveredEvent {
	private Long orderId;
	
	public OrderDeliveredEvent() {}
	
	public OrderDeliveredEvent(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
