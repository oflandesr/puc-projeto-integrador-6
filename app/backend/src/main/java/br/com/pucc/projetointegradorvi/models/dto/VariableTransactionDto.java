package br.com.pucc.projetointegradorvi.models.dto;

public class VariableTransactionDto {
	private String ticker;
	private Double amountBuyed;
	private Double amountSold;
	private Double averageInvested;

	public VariableTransactionDto(String ticker, Double amountBuyed, Double amountSold, Double averageInvested) {
		this.ticker = ticker;
		this.amountBuyed = amountBuyed;
		this.amountSold = amountSold;
		this.averageInvested = averageInvested;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
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
