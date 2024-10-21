package br.com.pucc.projetointegradorvi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.TickerModel;
import br.com.pucc.projetointegradorvi.repositories.TickerRepository;


@Service
public class TickerServiceImp implements TickerService {

	@Autowired
	TickerRepository tickerRepository;

	@Override
	public List<TickerModel> getTicker() {
		return this.tickerRepository.findAll();
	}

}
