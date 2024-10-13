package br.com.pucc.projetointegradorvi.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER")
public class UserModel {
	
	@Id
	@Column(name = "ID", nullable = false)
	private Integer id;

	@Column(name = "LOGIN", nullable = false)
	private String login;
	
	@JsonIgnore
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "ENABLED", nullable = false)
	private Boolean enabled = true;
	
	//@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSION", joinColumns = @JoinColumn(name = "LOGIN"), inverseJoinColumns = @JoinColumn(name = "ROLE"))
	Set<RoleModel2> roles;

	public UserModel() {
	}

	public UserModel(String login, String password, String firstName, String lastName, Set<RoleModel2> role) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	// Getters and setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<RoleModel2> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel2> roles) {
		this.roles = roles;
	}
}