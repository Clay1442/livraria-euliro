package br.com.euliro.livraria.dto;

import java.time.LocalDate;

import br.com.euliro.livraria.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {
	
	@NotBlank(message = "O nome não pode ser vazio")
    private String name;
    
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;
    
    @NotBlank
    private LocalDate birthDate;

    
    
    
    
	public UserCreateDTO(User entity) {
		super();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.password = entity.getPassword();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
    
}
