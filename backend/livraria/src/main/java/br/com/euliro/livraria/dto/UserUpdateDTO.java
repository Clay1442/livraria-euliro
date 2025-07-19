package br.com.euliro.livraria.dto;

import java.time.LocalDate;

import br.com.euliro.livraria.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateDTO {

	@NotBlank(message = "O nome não pode ser vazio")
	private String name;

	@Email(message = "Formato de email inválido")
	private String email;
    
	private LocalDate birthDate;

	public UserUpdateDTO(){
	}
	
	
	
	
	public UserUpdateDTO(User entity) {
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
