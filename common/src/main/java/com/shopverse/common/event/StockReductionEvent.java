package com.shopverse.common.event;

import java.util.List;

public class StockReductionEvent {
	private Long orderId;
	private List<StockItem> items;
	
	public StockReductionEvent() {}
	
	public StockReductionEvent(Long orderId, List<StockItem> items) {
		this.orderId = orderId;
		this.items = items;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<StockItem> getItems() {
		return items;
	}

	public void setItems(List<StockItem> items) {
		this.items = items;
	}
}
