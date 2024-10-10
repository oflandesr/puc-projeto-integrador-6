package br.com.pucc.projetointegradorvi.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TICKER")
public class TickerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ticker;

    private String currency;
    private String shortName;
    private String longName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String website;
    private String logoUrl;
    private String industry;
    private String sector;
    private Integer numberOfEmployees;
    private Float marketCap;

    // Outros atributos de mercado
    private Float regularMarketOpen;
    private Float regularMarketClose;
    private Float regularMarketPrice;
    private Float regularMarketVolume;

    @OneToMany(mappedBy = "ticker", cascade = CascadeType.ALL)
    private List<PriceModel> prices;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

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

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public Float getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(Float marketCap) {
		this.marketCap = marketCap;
	}

	public Float getRegularMarketOpen() {
		return regularMarketOpen;
	}

	public void setRegularMarketOpen(Float regularMarketOpen) {
		this.regularMarketOpen = regularMarketOpen;
	}

	public Float getRegularMarketClose() {
		return regularMarketClose;
	}

	public void setRegularMarketClose(Float regularMarketClose) {
		this.regularMarketClose = regularMarketClose;
	}

	public Float getRegularMarketPrice() {
		return regularMarketPrice;
	}

	public void setRegularMarketPrice(Float regularMarketPrice) {
		this.regularMarketPrice = regularMarketPrice;
	}

	public Float getRegularMarketVolume() {
		return regularMarketVolume;
	}

	public void setRegularMarketVolume(Float regularMarketVolume) {
		this.regularMarketVolume = regularMarketVolume;
	}

	public List<PriceModel> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceModel> prices) {
		this.prices = prices;
	}

}
