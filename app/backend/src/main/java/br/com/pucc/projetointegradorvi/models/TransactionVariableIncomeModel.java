package br.com.pucc.projetointegradorvi.models;

import java.sql.Date;

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
@Table(name = "TRANSACTION_VARIABLE_INCOME")
public class TransactionVariableIncomeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletModel wallet;

    @ManyToOne
    @JoinColumn(name = "ticker_id", nullable = false)
    private TickerModel ticker;

    @Column(name = "buy_or_sale", nullable = false)
    private Integer buyOrSale;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Float amount;

    private Float price;

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

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	
}
