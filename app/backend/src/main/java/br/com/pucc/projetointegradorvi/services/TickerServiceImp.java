package br.com.pucc.projetointegradorvi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.TickerModel;
import br.com.pucc.projetointegradorvi.repositories.TickerRepository;

@Service
public class TickerServiceImp implements TickerService {

	@Autowired
	TickerRepository tickerRepository;

	@Override
	public List<TickerModel> getTicker(Optional<String> id) {
		List<TickerModel> res = new ArrayList<TickerModel>();
		if (id.isPresent()) {
			Optional<TickerModel> t = this.tickerRepository.findById(id.get());
			if (t.isPresent()) {
				res.add(t.get());
			}
		} else {
			res = this.tickerRepository.findAll();
		}
		return res;
	}

}
