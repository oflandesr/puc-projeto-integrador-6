package br.com.pucc.projetointegradorvi.models.dto;

import br.com.pucc.projetointegradorvi.models.UserModel;

public class UserCreationDtoRes {

	private UserModel user;
	private ResponseReturnDto retorno;

	public UserCreationDtoRes() {
		this.retorno = new ResponseReturnDto();
	}

	public UserCreationDtoRes(String message) {
		this.retorno.setMensagem(message);
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public ResponseReturnDto getRetorno() {
		return retorno;
	}

	public void setRetorno(ResponseReturnDto retorno) {
		this.retorno = retorno;
	}

}
