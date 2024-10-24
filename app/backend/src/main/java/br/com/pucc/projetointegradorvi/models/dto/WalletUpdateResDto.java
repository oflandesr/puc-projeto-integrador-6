package br.com.pucc.projetointegradorvi.models.dto;

import br.com.pucc.projetointegradorvi.models.WalletModel;

public class WalletUpdateResDto {
	private Long id;
	private String name;
	private String objective;
	private Integer intenFixIncPercent;
	private Integer intenStockPercent;
	private Integer intenFilPercent;

	public WalletUpdateResDto() {
	}

	public WalletUpdateResDto(WalletModel wallet) {
		this.id = wallet.getId();
		this.name = wallet.getName();
		this.objective = wallet.getObjective();
		this.intenFixIncPercent = wallet.getIntenFilPercent();
		this.intenStockPercent = wallet.getIntenStockPercent();
		this.intenFilPercent = wallet.getIntenFilPercent();
	}

	public WalletUpdateResDto(String id, String name, String objective, Integer intenFixIncPercent,
			Integer intenStockPercent, Integer intenFilPercent) {
		this.id = Long.valueOf(id);
		this.name = name;
		this.objective = objective;
		this.intenFixIncPercent = intenFixIncPercent;
		this.intenStockPercent = intenStockPercent;
		this.intenFilPercent = intenFilPercent;
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
}
