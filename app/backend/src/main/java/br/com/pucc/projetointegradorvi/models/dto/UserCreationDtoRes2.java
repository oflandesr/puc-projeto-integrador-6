package br.com.pucc.projetointegradorvi.models.dto;

import br.com.pucc.projetointegradorvi.models.UserModel2;

public class UserCreationDtoRes2 {

	private UserModel2 user;
	private ResponseReturnDto result;

	public UserCreationDtoRes2() {
		this.result = new ResponseReturnDto();
	}

	public UserCreationDtoRes2(String message) {
		this.result.setMessage(message);
	}

	public UserModel2 getUser() {
		return user;
	}

	public void setUser(UserModel2 user) {
		this.user = user;
	}

	public ResponseReturnDto getResult() {
		return result;
	}

	public void setResult(ResponseReturnDto result) {
		this.result = result;
	}

}
