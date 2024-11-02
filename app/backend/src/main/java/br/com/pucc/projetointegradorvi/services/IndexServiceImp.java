package br.com.pucc.projetointegradorvi.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.IndexModel;
import br.com.pucc.projetointegradorvi.repositories.IndexRepository;

@Service
public class IndexServiceImp implements IndexService {

	@Autowired
	IndexRepository indexRepository;

	@Override
	public List<IndexModel> getIndex(Optional<String> date) {

		List<IndexModel> res = new ArrayList<IndexModel>();
		if (date.isPresent()) {
			Optional<IndexModel> i = this.indexRepository.findById(Date.valueOf(date.get()));
			if (i.isPresent()) {
				res.add(i.get());
			}
		} else {
			res = this.indexRepository.findAll();
		}
		return res;
	}
}
