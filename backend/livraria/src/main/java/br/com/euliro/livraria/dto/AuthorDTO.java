package br.com.euliro.livraria.dto;

import br.com.euliro.livraria.entities.Author;

public class AuthorDTO {

    private Long id;
    private String name;
    private String lastName;

    public AuthorDTO() {
    }

    public AuthorDTO(Author entity) {
        this.id = entity.getId(); 
        this.name = entity.getName();
        this.lastName = entity.getLastName();
    }

    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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