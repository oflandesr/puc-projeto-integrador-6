package br.com.pucc.projetointegradorvi.models.dto;

public class FixedTransactionWithVariationDto {
	private Double total;

	public FixedTransactionWithVariationDto(Double total) {
		this.total = total;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
