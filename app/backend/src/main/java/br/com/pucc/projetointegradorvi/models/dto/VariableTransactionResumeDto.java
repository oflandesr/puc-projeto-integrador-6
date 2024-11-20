package br.com.pucc.projetointegradorvi.models.dto;

public class VariableTransactionResumeDto {

	private Double amountBuyed;
	private Double amountSold;
	private Double averageInvested;

	public VariableTransactionResumeDto(Double amountBuyed, Double amountSold, Double averageInvested) {
		this.amountBuyed = amountBuyed;
		this.amountSold = amountSold;
		this.averageInvested = averageInvested;
	}

	public Double getAmountBuyed() {
		return amountBuyed;
	}

	public void setAmountBuyed(Double amountBuyed) {
		this.amountBuyed = amountBuyed;
	}

	public Double getAmountSold() {
		return amountSold;
	}

	public void setAmountSold(Double amountSold) {
		this.amountSold = amountSold;
	}

	public Double getAverageInvested() {
		return averageInvested;
	}

	public void setAverageInvested(Double averageInvested) {
		this.averageInvested = averageInvested;
	}
}
