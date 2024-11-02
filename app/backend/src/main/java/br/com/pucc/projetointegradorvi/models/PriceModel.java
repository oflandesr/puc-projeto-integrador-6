package br.com.pucc.projetointegradorvi.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRICES")
public class PriceModel {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "TIMESTAMP")
	private Long timestamp;

	@Column(name = "TICKER")
	private String ticker;

	@Column(name = "OPEN")
	private BigDecimal open;

	@Column(name = "HIGH")
	private BigDecimal high;

	@Column(name = "LOW")
	private BigDecimal low;

	@Column(name = "CLOSE")
	private BigDecimal close;

	@Column(name = "ADJUSTED_CLOSE")
	private BigDecimal adjustedClose;

	@Column(name = "VOLUME")
	private BigDecimal volume;

	// Getters e Setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getAdjustedClose() {
		return adjustedClose;
	}

	public void setAdjustedClose(BigDecimal adjustedClose) {
		this.adjustedClose = adjustedClose;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

}
