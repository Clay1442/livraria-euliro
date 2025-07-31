package br.com.euliro.livraria.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import br.com.euliro.livraria.entities.Book;

public class BookDTO {
 
	private Long id;
	private String title;
	private String description;
	private BigDecimal price;
    private Integer stock;
    private String imageUrl;
	private Set<AuthorDTO> authors = new HashSet<>();

	public BookDTO() {

	}

	public BookDTO(Book entity) {
	  this.id = entity.getId();
      this.title = entity.getTitle();
      this.description = entity.getDescription();
      this.price = entity.getPrice();
      this.stock = entity.getStock();
      this.imageUrl = entity.getImageUrl();
      this.authors = entity.getAuthors().stream().map(author -> new AuthorDTO(author)).collect(Collectors.toSet());
	}

	
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
	public Set<AuthorDTO> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<AuthorDTO> authors) {
		this.authors = authors;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	
	
}
