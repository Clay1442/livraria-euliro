package br.com.euliro.livraria.dto;

import java.util.Set;

import br.com.euliro.livraria.entities.Book;
import jakarta.validation.constraints.NotBlank;

public class BookUpdateDTO {

	@NotBlank(message = "O titulo não pode ser vazio")
	private String title;

	private String description;
	
    private Set<Long> authorIds;

	public BookUpdateDTO() {

	}

	public BookUpdateDTO(Book entity) {
		this.title = entity.getTitle();
		this.description = entity.getDescription();
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

	public Set<Long> getAuthorIds() {
		return authorIds;
	}

	public void setAuthorIds(Set<Long> authorIds) {
		this.authorIds = authorIds;
	}

}
