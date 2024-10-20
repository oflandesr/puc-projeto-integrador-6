package br.com.pucc.projetointegradorvi.models.dto;

import java.util.List;

import br.com.pucc.projetointegradorvi.models.FixedTransactionModel;
import br.com.pucc.projetointegradorvi.models.VariableTransactionModel;
import br.com.pucc.projetointegradorvi.models.WalletModel;

public class WalletCreationResDto {
	private Long id;
	private Long user;
	private String name;
	private String objective;
	private String intenFixIncPercent;
	private String intenStockPercent;
	private String intenFilPercent;
	private List<FixedTransactionModel> fixedIncomeTransactions;
	private List<VariableTransactionModel> variableIncomeTransactions;

	public WalletCreationResDto(WalletModel wallet) {
		this.id = Long.valueOf(wallet.getId());
		this.user = Long.valueOf(wallet.getUser().getId());
		this.name = wallet.getName();
		this.objective = wallet.getObjective();
		this.intenFixIncPercent = String.valueOf(wallet.getIntenFixIncPercent());
		this.intenStockPercent = String.valueOf(wallet.getIntenStockPercent());
		this.intenFilPercent = String.valueOf(wallet.getIntenFilPercent());
		if (!wallet.getFixedIncomeTransactions().isEmpty()) {
			this.fixedIncomeTransactions = wallet.getFixedIncomeTransactions();
		} else {
			this.fixedIncomeTransactions = List.of();
		}
		if (!wallet.getVariableIncomeTransactions().isEmpty()) {
			this.variableIncomeTransactions = wallet.getVariableIncomeTransactions();
		} else {
			this.variableIncomeTransactions = List.of();
		}
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

	public String getIntenFixIncPercent() {
		return intenFixIncPercent;
	}

	public void setIntenFixIncPercent(String intenFixIncPercent) {
		this.intenFixIncPercent = intenFixIncPercent;
	}

	public String getIntenStockPercent() {
		return intenStockPercent;
	}

	public void setIntenStockPercent(String intenStockPercent) {
		this.intenStockPercent = intenStockPercent;
	}

	public String getIntenFilPercent() {
		return intenFilPercent;
	}

	public void setIntenFilPercent(String intenFilPercent) {
		this.intenFilPercent = intenFilPercent;
	}

	public List<FixedTransactionModel> getFixedIncomeTransactions() {
		return fixedIncomeTransactions;
	}

	public void setFixedIncomeTransactions(List<FixedTransactionModel> fixedIncomeTransactions) {
		this.fixedIncomeTransactions = fixedIncomeTransactions;
	}

	public List<VariableTransactionModel> getVariableIncomeTransactions() {
		return variableIncomeTransactions;
	}

	public void setVariableIncomeTransactions(List<VariableTransactionModel> variableIncomeTransactions) {
		this.variableIncomeTransactions = variableIncomeTransactions;
	}
}
