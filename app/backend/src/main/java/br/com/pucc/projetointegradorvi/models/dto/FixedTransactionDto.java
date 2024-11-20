package br.com.pucc.projetointegradorvi.models.dto;

import java.time.LocalDate;

public class FixedTransactionDto {
	private Long id;
	private String institution;
	private String type;
	private Double value;
	private LocalDate startDate;
	private LocalDate endDate;
	private String indexName;
	private Double taxValue;
	private Integer timeAppliedInDays;
	private Double currentValue;

	public FixedTransactionDto(Long id, String institution, String type, Double value, LocalDate startDate,
			LocalDate endDate, String indexName, Double taxValue, Integer timeAppliedInDays, Double currentValue) {
		this.id = id;
		this.institution = institution;
		this.type = type;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.indexName = indexName;
		this.taxValue = taxValue;
		this.timeAppliedInDays = timeAppliedInDays;
		this.currentValue = currentValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
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

	public Integer getTimeAppliedInDays() {
		return timeAppliedInDays;
	}

	public void setTimeAppliedInDays(Integer timeAppliedInDays) {
		this.timeAppliedInDays = timeAppliedInDays;
	}

	public Double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Double currentValue) {
		this.currentValue = currentValue;
	}

}
