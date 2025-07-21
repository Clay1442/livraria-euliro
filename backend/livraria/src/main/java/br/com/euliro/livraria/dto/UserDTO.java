package br.com.euliro.livraria.dto;

import java.time.LocalDate;

import br.com.euliro.livraria.entities.User;

public class UserDTO {
    private Long id;
	private String name;
	private String email;
	private LocalDate birthDate;
	
	public UserDTO(){
	}
		
	public UserDTO(User entity) {
		super();
		this.id = entity.getId();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	
}
