package br.com.pucc.projetointegradorvi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TICKERS")
public class TickerModel {

	@Id
	@Column(name = "TICKER")
	private String ticker;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "SHORT_NAME")
	private String shortName;

	@Column(name = "LONG_NAME")
	private String longName;

	@Column(name = "ADDRESS_2")
	private String address2;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "WEBSITE")
	private String website;

//	@Column(name = "LOGO_URL")
//	private String logoUrl;

	@Column(name = "INDUSTRY")
	private String industry;

	@Column(name = "SECTOR")
	private String sector;

//	@Column(name = "BUSINESS_SUMMARY")
//	private String businessSummary;

	@Column(name = "NUMBER_OF_EMPLOYEES")
	private Integer numberOfEmployees;

//	@Column(name = "MARKET_CAP")
//	private Float marketCap;
//
//	@Column(name = "200_DAY_AVERAGE")
//	private Float twoHundredDayAverage;
//
//	@Column(name = "200_DAY_AVERAGE_CHANGE")
//	private Float twoHundredDayAverageChange;
//
//	@Column(name = "200_DAY_AVERAGE_CHANGE_PERCENT")
//	private Float twoHundredDayAverageChangePercent;

	@Column(name = "REGULAR_MARKET_CHANGE")
	private Float regularMarketChange;

	@Column(name = "REGULAR_MARKET_PRICE")
	private Float regularMarketPrice;

	@Column(name = "REGULAR_MARKET_DAY_HIGH")
	private Float regularMarketDayHigh;

	@Column(name = "REGULAR_MARKET_DAY_LOW")
	private Float regularMarketDayLow;

	@Column(name = "REGULAR_MARKET_VOLUME")
	private Integer regularMarketVolume;

	@Column(name = "REGULAR_MARKET_OPEN")
	private Float regularMarketOpen;

//	@Column(name = "AVERAGE_DAILY_VOLUME_10_DAYS")
//	private Integer averageDailyVolume10Days;
//
//	@Column(name = "52_WEEKS_HIGH_CHANGE")
//	private Float fiftyTwoWeeksHighChange;
//
//	@Column(name = "52_WEEKS_HIGH_CHANGE_PERCENT")
//	private Float fiftyTwoWeeksHighChangePercent;
//
//	@Column(name = "52_WEEKS_RANGE")
//	private String fiftyTwoWeeksRange;
//
//	@Column(name = "52_WEEKS_HIGH")
//	private Float fiftyTwoWeeksHigh;

	@Column(name = "PRICE_EARNINGS")
	private Float priceEarnings;

	@Column(name = "EARNINGS_PER_SHARE")
	private Float earningsPerShare;

	// Construtor padrão
	public TickerModel() {
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

//	public String getLogoUrl() {
//		return logoUrl;
//	}
//
//	public void setLogoUrl(String logoUrl) {
//		this.logoUrl = logoUrl;
//	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

//	public String getBusinessSummary() {
//		return businessSummary;
//	}
//
//	public void setBusinessSummary(String businessSummary) {
//		this.businessSummary = businessSummary;
//	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

//	public Float getMarketCap() {
//		return marketCap;
//	}
//
//	public void setMarketCap(Float marketCap) {
//		this.marketCap = marketCap;
//	}
//
//	public Float getTwoHundredDayAverage() {
//		return twoHundredDayAverage;
//	}
//
//	public void setTwoHundredDayAverage(Float twoHundredDayAverage) {
//		this.twoHundredDayAverage = twoHundredDayAverage;
//	}
//
//	public Float getTwoHundredDayAverageChange() {
//		return twoHundredDayAverageChange;
//	}
//
//	public void setTwoHundredDayAverageChange(Float twoHundredDayAverageChange) {
//		this.twoHundredDayAverageChange = twoHundredDayAverageChange;
//	}
//
//	public Float getTwoHundredDayAverageChangePercent() {
//		return twoHundredDayAverageChangePercent;
//	}
//
//	public void setTwoHundredDayAverageChangePercent(Float twoHundredDayAverageChangePercent) {
//		this.twoHundredDayAverageChangePercent = twoHundredDayAverageChangePercent;
//	}

	public Float getRegularMarketChange() {
		return regularMarketChange;
	}

	public void setRegularMarketChange(Float regularMarketChange) {
		this.regularMarketChange = regularMarketChange;
	}

	public Float getRegularMarketPrice() {
		return regularMarketPrice;
	}

	public void setRegularMarketPrice(Float regularMarketPrice) {
		this.regularMarketPrice = regularMarketPrice;
	}

	public Float getRegularMarketDayHigh() {
		return regularMarketDayHigh;
	}

	public void setRegularMarketDayHigh(Float regularMarketDayHigh) {
		this.regularMarketDayHigh = regularMarketDayHigh;
	}

	public Float getRegularMarketDayLow() {
		return regularMarketDayLow;
	}

	public void setRegularMarketDayLow(Float regularMarketDayLow) {
		this.regularMarketDayLow = regularMarketDayLow;
	}

	public Integer getRegularMarketVolume() {
		return regularMarketVolume;
	}

	public void setRegularMarketVolume(Integer regularMarketVolume) {
		this.regularMarketVolume = regularMarketVolume;
	}

	public Float getRegularMarketOpen() {
		return regularMarketOpen;
	}

	public void setRegularMarketOpen(Float regularMarketOpen) {
		this.regularMarketOpen = regularMarketOpen;
	}

//	public Integer getAverageDailyVolume10Days() {
//		return averageDailyVolume10Days;
//	}
//
//	public void setAverageDailyVolume10Days(Integer averageDailyVolume10Days) {
//		this.averageDailyVolume10Days = averageDailyVolume10Days;
//	}
//
//	public Float getFiftyTwoWeeksHighChange() {
//		return fiftyTwoWeeksHighChange;
//	}
//
//	public void setFiftyTwoWeeksHighChange(Float fiftyTwoWeeksHighChange) {
//		this.fiftyTwoWeeksHighChange = fiftyTwoWeeksHighChange;
//	}
//
//	public Float getFiftyTwoWeeksHighChangePercent() {
//		return fiftyTwoWeeksHighChangePercent;
//	}
//
//	public void setFiftyTwoWeeksHighChangePercent(Float fiftyTwoWeeksHighChangePercent) {
//		this.fiftyTwoWeeksHighChangePercent = fiftyTwoWeeksHighChangePercent;
//	}
//
//	public String getFiftyTwoWeeksRange() {
//		return fiftyTwoWeeksRange;
//	}
//
//	public void setFiftyTwoWeeksRange(String fiftyTwoWeeksRange) {
//		this.fiftyTwoWeeksRange = fiftyTwoWeeksRange;
//	}
//
//	public Float getFiftyTwoWeeksHigh() {
//		return fiftyTwoWeeksHigh;
//	}
//
//	public void setFiftyTwoWeeksHigh(Float fiftyTwoWeeksHigh) {
//		this.fiftyTwoWeeksHigh = fiftyTwoWeeksHigh;
//	}

	public Float getPriceEarnings() {
		return priceEarnings;
	}

	public void setPriceEarnings(Float priceEarnings) {
		this.priceEarnings = priceEarnings;
	}

	public Float getEarningsPerShare() {
		return earningsPerShare;
	}

	public void setEarningsPerShare(Float earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}

}
