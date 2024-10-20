package br.com.pucc.projetointegradorvi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRICE")
public class PriceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timestamp;

    @ManyToOne
    @JoinColumn(name = "TICKER", nullable = false)
    private TickerModel ticker;

    private Float open;
    private Float high;
    private Float low;
    private Float close;

    @Column(name = "ADJUSTED_CLOSE")
    private Float adjustedClose;

    private Float volume;

	public Integer getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}

	public TickerModel getTicker() {
		return ticker;
	}

	public void setTicker(TickerModel ticker) {
		this.ticker = ticker;
	}

	public Float getOpen() {
		return open;
	}

	public void setOpen(Float open) {
		this.open = open;
	}

	public Float getHigh() {
		return high;
	}

	public void setHigh(Float high) {
		this.high = high;
	}

	public Float getLow() {
		return low;
	}

	public void setLow(Float low) {
		this.low = low;
	}

	public Float getClose() {
		return close;
	}

	public void setClose(Float close) {
		this.close = close;
	}

	public Float getAdjustedClose() {
		return adjustedClose;
	}

	public void setAdjustedClose(Float adjustedClose) {
		this.adjustedClose = adjustedClose;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

}
