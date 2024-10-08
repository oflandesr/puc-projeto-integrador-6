package br.com.pucc.projetointegradorvi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.repositories.WalletRepository;

@Service
public class WalletServiceImp implements WalletService {
	
	@Autowired
	private WalletRepository walletRepository;

	@Override
	public Optional<WalletModel> getWalletByUserId(Long userId) {
		return walletRepository.findByUserId(userId);
	}

}
