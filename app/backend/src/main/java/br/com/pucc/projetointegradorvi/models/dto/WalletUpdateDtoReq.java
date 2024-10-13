package br.com.pucc.projetointegradorvi.models.dto;

public class WalletUpdateDtoReq {

	private String name;
	private String objective;
	private Integer intendedFixedIncomePercent;
	private Integer intendedStockPercent;
	private Integer intendedFiiPercent;

	public WalletUpdateDtoReq(String id, String name, String objective, Integer intendedFixedIncomePercent,
			Integer intendedStockPercent, Integer intendedFiiPercent) {

		this.name = name;
		this.objective = objective;
		this.intendedFixedIncomePercent = intendedFixedIncomePercent;
		this.intendedStockPercent = intendedStockPercent;
		this.intendedFiiPercent = intendedFiiPercent;

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

}
