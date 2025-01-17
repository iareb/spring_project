package it.corso.dto;

import java.util.Set;

import it.corso.model.Tipology;

public class UserDto {
	
	private String name;
	private String lastName;
	private String email;
	private String password;
	private Set<Tipology> roles;
	
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

	public Set<Tipology> getRoles() {
		return roles;
	}

	public void setRoles(Set<Tipology> roles) {
		this.roles = roles;
	}
	

}
