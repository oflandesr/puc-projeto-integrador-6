package br.com.pucc.projetointegradorvi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "WALLET")
public class WalletModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "OBJECTIVE", nullable = false)
	private String objective;

	@Column(name = "INTENDED_FIXED_INCOME_PERCENT")
	private Integer intendedFixedIncomePercent;

	@Column(name = "INTENDED_STOCK_PERCENT")
	private Integer intendedStockPercent;

	@Column(name = "INTENDED_FII_PERCENT")
	private Integer intendedFiiPercent;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserModel2 user;

	public WalletModel() {
	}

	public WalletModel(String name, String objective, String intendedFixedIncomePercent, String intendedStockPercent,
			String intendedFiiPercent, UserModel2 user) {
		this.name = name;
		this.objective = objective;
		this.intendedFixedIncomePercent = Integer.valueOf(intendedFixedIncomePercent);
		this.intendedStockPercent = Integer.valueOf(intendedStockPercent);
		this.intendedFiiPercent = Integer.valueOf(intendedFiiPercent);
		this.user = user;
	}

//    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
//    private List<TransactionVariableIncomeModel> variableIncomeTransactions;
//
//    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
//    private List<TransactionFixedIncomeModel> fixedIncomeTransactions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public Integer getIntendedFixedIncomePercent() {
		return intendedFixedIncomePercent;
	}

	public void setIntendedFixedIncomePercent(Integer intendedFixedIncomePercent) {
		this.intendedFixedIncomePercent = intendedFixedIncomePercent;
	}

	public Integer getIntendedStockPercent() {
		return intendedStockPercent;
	}

	public void setIntendedStockPercent(Integer intendedStockPercent) {
		this.intendedStockPercent = intendedStockPercent;
	}

	public Integer getIntendedFiiPercent() {
		return intendedFiiPercent;
	}

	public void setIntendedFiiPercent(Integer intendedFiiPercent) {
		this.intendedFiiPercent = intendedFiiPercent;
	}

	public UserModel2 getUser() {
		return user;
	}

	public void setUser(UserModel2 user) {
		this.user = user;
	}

//	public List<TransactionVariableIncomeModel> getVariableIncomeTransactions() {
//		return variableIncomeTransactions;
//	}
//
//	public void setVariableIncomeTransactions(List<TransactionVariableIncomeModel> variableIncomeTransactions) {
//		this.variableIncomeTransactions = variableIncomeTransactions;
//	}
//
//	public List<TransactionFixedIncomeModel> getFixedIncomeTransactions() {
//		return fixedIncomeTransactions;
//	}
//
//	public void setFixedIncomeTransactions(List<TransactionFixedIncomeModel> fixedIncomeTransactions) {
//		this.fixedIncomeTransactions = fixedIncomeTransactions;
//	}

}
