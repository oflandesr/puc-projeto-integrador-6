package br.com.pucc.projetointegradorvi.models.dto;

public class ResponseReturnDto {

	private String status = "000";
	private String id = "200_OK";
	private String mensagem = "Executado com sucesso!";
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
