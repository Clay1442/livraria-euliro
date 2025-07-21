package br.com.euliro.livraria.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.euliro.livraria.entities.Cart;

public class CartDTO {

    private Long id;
    private LocalDate creationDate;
    private LocalDate lastModifiedDate;
    private UserDTO client;
    private Set<CartItemDTO> items; 
    
	public CartDTO(){		
	}

	public CartDTO(Cart entity) {
		super();
		this.id = entity.getId();
		this.creationDate = entity.getCreationDate();
		this.lastModifiedDate = entity.getLastModifiedDate();
		this.client = new UserDTO(entity.getClient());
        this.items = entity.getItems().stream().map(item -> new CartItemDTO(item)).collect(Collectors.toSet());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getLastDateCreation() {
		return lastModifiedDate;
	}

	public void setLastDateCreation(LocalDate lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public UserDTO getClient() {
		return client;
	}

	public void setClient(UserDTO client) {
		this.client = client;
	}

	public Set<CartItemDTO> getItems() {
		return items;
	}

	public void setItems(Set<CartItemDTO> items) {
		this.items = items;
	}

	
}
