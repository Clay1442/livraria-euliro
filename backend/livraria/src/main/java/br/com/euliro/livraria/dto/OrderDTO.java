package br.com.euliro.livraria.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import br.com.euliro.livraria.entities.Order;
import br.com.euliro.livraria.entities.enums.OrderStatus;

public class OrderDTO {


    private Long id;
    private Instant orderDate;
    private OrderStatus orderStatus;
    private String trackingCode;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> items; 
    
	public OrderDTO(){		
	}

	public OrderDTO(Order entity) {
		super();
		this.id = entity.getId();
		this.orderDate = entity.getOrderDate();
		this.orderStatus = entity.getOrderStatus();
		this.trackingCode = entity.getTrackingCode();
		this.totalPrice = entity.getTotalPrice();
        this.items = entity.getItems().stream().map(item -> new OrderItemDTO(item)).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Instant orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTrackingCode() {
		return trackingCode;
	}

	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}
	
	
	
	
}
