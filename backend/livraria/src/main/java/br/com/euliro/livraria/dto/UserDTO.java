package br.com.euliro.livraria.dto;

import java.time.LocalDate;

import br.com.euliro.livraria.entities.User;

public class UserDTO {

	private String name;
	private String email;
	private LocalDate birthDate;
	
	public UserDTO(){
	}
		
	public UserDTO(User entity) {
		super();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.birthDate = entity.getBirthDate();
	}




	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	
	
	
}
