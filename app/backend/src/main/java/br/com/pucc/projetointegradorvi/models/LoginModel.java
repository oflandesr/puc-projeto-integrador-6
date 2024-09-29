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
@Table(name = "Login")
public class LoginModel {

	@Id
	@Column(name = "login", unique = true)
	private String login;
	
	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "enabled")
	private Boolean enabled = true;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Login_Role", joinColumns = @JoinColumn(name = "login"), inverseJoinColumns = @JoinColumn(name = "role"))
	Set<RoleModel> roles;

	public LoginModel() {
	}

	public LoginModel(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public LoginModel(String login, String password, Set<RoleModel> roles) {
		this.login = login;
		this.password = password;
		this.roles = roles;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

}
