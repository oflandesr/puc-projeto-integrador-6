package br.com.pucc.projetointegradorvi.models.dto;

public class FixedTransactionByInstitutionDto {
	private String institution;
	private Double value;

	public FixedTransactionByInstitutionDto(String institution, Double value) {
		this.institution = institution;
		this.value = value;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
