package br.com.pucc.projetointegradorvi.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.pucc.projetointegradorvi.models.dto.WalletDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "WALLETS")
public class WalletModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "OBJECTIVE", nullable = false)
	private String objective;

	@Column(name = "INTENDED_FIXED_INCOME_PERCENT")
	private Integer intenFixIncPercent;

	@Column(name = "INTENDED_STOCK_PERCENT")
	private Integer intenStockPercent;

	@Column(name = "INTENDED_FII_PERCENT")
	private Integer intenFilPercent;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserModel user;

	@OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
	private List<VariableTransactionModel> variableIncomeTransactions;

	@OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
	private List<FixedTransactionModel> fixedIncomeTransactions;

	public WalletModel() {
	}

	public WalletModel(String name, String objective, String intenFixIncPercent, String intenStockPercent,
			String intenFilPercent, UserModel user) {
		this.name = name;
		this.objective = objective;
		this.intenFixIncPercent = Integer.valueOf(intenFixIncPercent);
		this.intenStockPercent = Integer.valueOf(intenStockPercent);
		this.intenFilPercent = Integer.valueOf(intenFilPercent);
		this.user = user;
		this.variableIncomeTransactions = List.of();
		this.fixedIncomeTransactions = List.of();
	}

	public WalletDto convertToDto() {
		return new WalletDto(this.id, this.user.getId(), this.name, this.objective, this.intenFixIncPercent,
				this.intenStockPercent, this.intenFilPercent,
				this.fixedIncomeTransactions == null ? List.of() : this.fixedIncomeTransactions,
				this.variableIncomeTransactions == null ? List.of() : this.variableIncomeTransactions);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getIntenFixIncPercent() {
		return intenFixIncPercent;
	}

	public void setIntenFixIncPercent(Integer intenFixIncPercent) {
		this.intenFixIncPercent = intenFixIncPercent;
	}

	public Integer getIntenStockPercent() {
		return intenStockPercent;
	}

	public void setIntenStockPercent(Integer intenStockPercent) {
		this.intenStockPercent = intenStockPercent;
	}

	public Integer getIntenFilPercent() {
		return intenFilPercent;
	}

	public void setIntenFilPercent(Integer intenFilPercent) {
		this.intenFilPercent = intenFilPercent;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public List<VariableTransactionModel> getVariableIncomeTransactions() {
		return variableIncomeTransactions;
	}

	public void setVariableIncomeTransactions(List<VariableTransactionModel> variableIncomeTransactions) {
		this.variableIncomeTransactions = variableIncomeTransactions;
	}

	public List<FixedTransactionModel> getFixedIncomeTransactions() {
		return fixedIncomeTransactions;
	}

	public void setFixedIncomeTransactions(List<FixedTransactionModel> fixedIncomeTransactions) {
		this.fixedIncomeTransactions = fixedIncomeTransactions;
	}

}
