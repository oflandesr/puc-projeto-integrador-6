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
@Table(name = "TRANSACTION_FIXED_INCOME")
public class FixedTransactionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "WALLET_ID", nullable = false)
	private WalletModel wallet;

	@Column(name = "INSTITUTION")
	private String institution;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "VALUE")
	private Double value;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "INDEX_NAME")
	private String indexName;

	@Column(name = "TAX_VALUE")
	private Double taxValue;

	public FixedTransactionModel() {
	}

	public FixedTransactionModel(WalletModel wallet, String institution, String type, String value, String startDate,
			String endDate, String indexName, String taxValue) {
		this.wallet = wallet;
		this.institution = institution;
		this.type = type;
		this.value = Double.valueOf(value);
		this.startDate = Date.valueOf(startDate);
		this.endDate = Date.valueOf(endDate);
		this.indexName = indexName;
		this.taxValue = Double.valueOf(taxValue);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WalletModel getWallet() {
		return wallet;
	}

	public void setWallet(WalletModel wallet) {
		this.wallet = wallet;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}

}
