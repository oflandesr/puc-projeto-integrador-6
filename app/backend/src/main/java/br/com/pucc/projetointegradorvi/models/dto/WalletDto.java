package br.com.pucc.projetointegradorvi.models.dto;

import java.util.List;

import br.com.pucc.projetointegradorvi.models.FixedTransactionModel;
import br.com.pucc.projetointegradorvi.models.VariableTransactionModel;

public class WalletDto {

	private Long id;
	private Long user;
	private String name;
	private String objective;
	private Integer intenFixIncPercent;
	private Integer intenStockPercent;
	private Integer intenFilPercent;
	private List<VariableTransactionModel> variableIncomeTransactions;
	private List<FixedTransactionModel> fixedIncomeTransactions;

	public WalletDto(Long id, Long userId, String name, String objective, Integer intenFixIncPercent,
			Integer intenStockPercent, Integer intenFilPercent, List<FixedTransactionModel> fixedIncomeTransactions,
			List<VariableTransactionModel> variableIncomeTransactions) {
		this.id = id;
		this.name = name;
		this.objective = objective;
		this.intenFixIncPercent = intenFixIncPercent;
		this.intenStockPercent = intenStockPercent;
		this.intenFilPercent = intenFilPercent;
		this.user = userId;
		this.variableIncomeTransactions = variableIncomeTransactions;
		this.fixedIncomeTransactions = fixedIncomeTransactions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
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