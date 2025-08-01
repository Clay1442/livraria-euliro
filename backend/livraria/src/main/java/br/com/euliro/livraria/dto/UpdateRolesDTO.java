package br.com.euliro.livraria.dto;

import java.util.Set;

import br.com.euliro.livraria.entities.enums.Role;

public class UpdateRolesDTO {

	private Set<Role> roles;

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
}
