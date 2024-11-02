package br.com.pucc.projetointegradorvi.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "INDEXES")
public class IndexModel {
	@Id
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	private Date date;

	@Column(name = "SELIC")
	private Double selic;

	@Column(name = "CDI")
	private Double cdi;

	@Column(name = "IPCA")
	private Double ipca;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getSelic() {
		return selic;
	}

	public void setSelic(Double selic) {
		this.selic = selic;
	}

	public Double getCdi() {
		return cdi;
	}

	public void setCdi(Double cdi) {
		this.cdi = cdi;
	}

	public Double getIpca() {
		return ipca;
	}

	public void setIpca(Double ipca) {
		this.ipca = ipca;
	}

}
