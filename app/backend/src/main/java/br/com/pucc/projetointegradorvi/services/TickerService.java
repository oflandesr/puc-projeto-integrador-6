package br.com.pucc.projetointegradorvi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.TickerModel;

@Service
public interface TickerService {
	public List<TickerModel> getTicker();
}
