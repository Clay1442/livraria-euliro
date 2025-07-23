package br.com.euliro.livraria.dto;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;


public class BookCreateDTO {
	
	@NotBlank(message = "O título não pode estar em branco") 
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
	private String title;
	
	
	private String description;
	
	@NotNull(message = "O preço não pode ser nulo")
	@Positive(message = "O preço deve ser um valor positivo")
	private BigDecimal price;
	
	@NotNull(message = "O estoque não pode ser nulo")
    @PositiveOrZero(message = "O estoque deve ser um valor positivo ou zero")
	private Integer stock;
	
    @NotEmpty(message = "O livro deve ter pelo menos um autor") 
	private Set<Long> authorIds;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Set<Long> getAuthorIds() {
		return authorIds;
	}

	public void setAuthorIds(Set<Long> authorIds) {
		this.authorIds = authorIds;
	}

}
