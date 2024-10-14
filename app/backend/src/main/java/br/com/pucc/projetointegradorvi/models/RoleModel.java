package br.com.pucc.projetointegradorvi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLE")
public class RoleModel {
	
	@Id
	@Column(name = "ROLE")
	private String role;

	@Column(name = "DESCRIPTION")
	private String description;
	
	public RoleModel() {
	}

	public RoleModel(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
