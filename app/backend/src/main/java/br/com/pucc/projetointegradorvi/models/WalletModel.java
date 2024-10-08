package br.com.pucc.projetointegradorvi.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "WALLET")
public class WalletModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String objective;

    @Column(name = "intended_fixed_income_percent")
    private Integer intendedFixedIncomePercent;

    @Column(name = "intended_stock_percent")
    private Integer intendedStockPercent;

    @Column(name = "intended_fii_percent")
    private Integer intendedFiiPercent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<TransactionVariableIncomeModel> variableIncomeTransactions;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<TransactionFixedIncomeModel> fixedIncomeTransactions;

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

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public List<TransactionVariableIncomeModel> getVariableIncomeTransactions() {
		return variableIncomeTransactions;
	}

	public void setVariableIncomeTransactions(List<TransactionVariableIncomeModel> variableIncomeTransactions) {
		this.variableIncomeTransactions = variableIncomeTransactions;
	}

	public List<TransactionFixedIncomeModel> getFixedIncomeTransactions() {
		return fixedIncomeTransactions;
	}

	public void setFixedIncomeTransactions(List<TransactionFixedIncomeModel> fixedIncomeTransactions) {
		this.fixedIncomeTransactions = fixedIncomeTransactions;
	}

}
