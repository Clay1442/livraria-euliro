package br.com.euliro.livraria.dto;

import java.math.BigDecimal;

import br.com.euliro.livraria.entities.OrderItem;

public class OrderItemDTO {

    private String bookTitle;
    private String description;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

   public OrderItemDTO() {
    }

    public OrderItemDTO(OrderItem entity) {
        this.bookTitle = entity.getBook().getTitle(); 
        this.setDescription(entity.getBook().getDescription());
        this.quantity = entity.getQuantity();
        this.unitPrice = entity.getUnitPrice();
        this.subtotal = entity.getSubtotal();   
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
