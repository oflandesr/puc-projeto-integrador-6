package br.com.pucc.projetointegradorvi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.WalletModel;

@Service
public interface WalletService {

	public Optional<WalletModel> getWalletByUserId(Long userId);
	
}
