package br.com.euliro.livraria.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddItemRequestDTO {

	@NotNull
	private Long bookId;

    @Positive(message = "A quantidade deve ser positiva")
	@NotNull(message = "A quantidade não pode ser nulo")
	private Integer quantity;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
