package br.com.pucc.projetointegradorvi.models.dto;

public class UserCreationDtoReq {

	private String firstName;
	private String lastName;
	private AccessDto acesso;

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

	public AccessDto getAcesso() {
		return acesso;
	}

	public void setAcesso(AccessDto acesso) {
		this.acesso = acesso;
	}
}