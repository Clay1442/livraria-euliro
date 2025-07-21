package br.com.euliro.livraria.dto;

import br.com.euliro.livraria.entities.Author;

public class AuthorDTO {

	
    private String name;
    
    private String lastName;

    public AuthorDTO() {
    	
    }
    
    
	public AuthorDTO(Author entity) {
		super();
		this.name = entity.getName();
		this.lastName = entity.getLastName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
