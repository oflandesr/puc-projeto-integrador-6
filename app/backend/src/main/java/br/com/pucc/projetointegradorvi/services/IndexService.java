package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.IndexModel;

@Service
public interface IndexService {
	public List<IndexModel> getIndex(Optional<String> date);
}
