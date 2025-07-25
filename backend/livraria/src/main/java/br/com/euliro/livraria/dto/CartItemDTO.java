package br.com.euliro.livraria.dto;

import java.math.BigDecimal;

import br.com.euliro.livraria.entities.CartItem;

public class CartItemDTO {

	private Long id;
	private BookDTO book;
	private BigDecimal unitPrice;
	private Integer quantity;
    private BigDecimal subtotal; 


	public CartItemDTO() {
	}

	public CartItemDTO(CartItem entity) {
		this.id = entity.getId();
		this.book = new BookDTO(entity.getBook());
		this.unitPrice = entity.getUnitPrice();
		this.quantity = entity.getQuantity();
		this.subtotal = entity.getSubtotal();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BookDTO getBook() {
		return book;
	}

	public void setBook(BookDTO book) {
		this.book = book;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setAmount(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

}