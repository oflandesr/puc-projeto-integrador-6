package br.com.pucc.projetointegradorvi.models.dto;

import br.com.pucc.projetointegradorvi.models.UserModel;

public class UserCreationDtoRes {

	private UserModel user;
	private ResponseReturnDto result;

	public UserCreationDtoRes() {
		this.result = new ResponseReturnDto();
	}

	public UserCreationDtoRes(String message) {
		this.result.setMessage(message);
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public ResponseReturnDto getResult() {
		return result;
	}

	public void setResult(ResponseReturnDto result) {
		this.result = result;
	}

}
