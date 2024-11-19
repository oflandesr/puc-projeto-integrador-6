package br.com.pucc.projetointegradorvi.models.dto;

public class FixedTransactionWithVariationByInstitutionDto {

	private String institution;
	private Double value;
	private Double currentValue;
	private Double gain;
	private Double percentageGain;

	public FixedTransactionWithVariationByInstitutionDto(String institution, Double value, Double currentValue,
			Double gain, Double percentageGain) {
		this.institution = institution;
		this.value = value;
		this.currentValue = currentValue;
		this.gain = gain;
		this.percentageGain = percentageGain;
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

	public Double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Double currentValue) {
		this.currentValue = currentValue;
	}

	public Double getGain() {
		return gain;
	}

	public void setGain(Double gain) {
		this.gain = gain;
	}

	public Double getPercentageGain() {
		return percentageGain;
	}

	public void setPercentageGain(Double percentageGain) {
		this.percentageGain = percentageGain;
	}

}
