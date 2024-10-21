package br.com.pucc.projetointegradorvi.models.dto;

public class VariableTransactionDto {

	private String ticker;

	private String buyOrSale;

	private String date;

	private String amount;

	private String price;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getBuyOrSale() {
		return buyOrSale;
	}

	public void setBuyOrSale(String buyOrSale) {
		this.buyOrSale = buyOrSale;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
