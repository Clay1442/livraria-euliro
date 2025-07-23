package br.com.euliro.livraria.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthorCreateDTO {

	@NotBlank(message = "O nome não pode ser vazio")
    private String name;

	@NotBlank(message = "O sobrenome não pode ser vazio")
    private String lastName;
    
	
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
