package br.com.pucc.projetointegradorvi.models.dto;

public class WalletReqDto {

	private String name;
	private String objective;
	private String intenFixIncPercent;
	private String intenStockPercent;
	private String intenFilPercent;

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

}
