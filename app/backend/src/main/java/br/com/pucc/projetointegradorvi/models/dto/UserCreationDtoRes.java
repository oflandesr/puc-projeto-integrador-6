package br.com.pucc.projetointegradorvi.models.dto;

public class UserCreationDtoRes {

	private String user;
	private ResponseReturnDto retorno;

	public UserCreationDtoRes() {
	}

	public UserCreationDtoRes(String message) {
		this.retorno.setMensagem(message);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ResponseReturnDto getRetorno() {
		return retorno;
	}

	public void setRetorno(ResponseReturnDto retorno) {
		this.retorno = retorno;
	}

}
