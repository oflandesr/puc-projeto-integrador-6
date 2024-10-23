package br.com.pucc.projetointegradorvi.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "TRANSACTIONS_VARIABLE_INCOME")
public class VariableTransactionModel {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "WALLET_ID")
	private WalletModel wallet;

	@ManyToOne
	@JoinColumn(name = "TICKER_ID")
	private TickerModel ticker;

	@Column(name = "BUY_OR_SALE")
	private Integer buyOrSale;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	private Date date;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "PRICE")
	private Double price;

	public VariableTransactionModel() {

	}

	public VariableTransactionModel(WalletModel wallet, TickerModel ticker, String buyOrSale, String date,
			String amount, String price) {

		this.wallet = wallet;
		this.ticker = ticker;
		this.buyOrSale = Integer.valueOf(buyOrSale);
		this.date = Date.valueOf(date);
		this.amount = Double.valueOf(amount);
		this.price = Double.valueOf(price);

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WalletModel getWallet() {
		return wallet;
	}

	public void setWallet(WalletModel wallet) {
		this.wallet = wallet;
	}

	public TickerModel getTicker() {
		return ticker;
	}

	public void setTicker(TickerModel ticker) {
		this.ticker = ticker;
	}

	public Integer getBuyOrSale() {
		return buyOrSale;
	}

	public void setBuyOrSale(Integer buyOrSale) {
		this.buyOrSale = buyOrSale;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
