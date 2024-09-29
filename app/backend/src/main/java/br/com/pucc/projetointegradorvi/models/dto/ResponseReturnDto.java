package br.com.pucc.projetointegradorvi.models.dto;

public class ResponseReturnDto {

	private String id = "200";
	private String message = "Executado com sucesso!";
	
	public ResponseReturnDto() {}
	
	public ResponseReturnDto(String id, String mensagem) {
		this.id = id;
		this.message = mensagem;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
