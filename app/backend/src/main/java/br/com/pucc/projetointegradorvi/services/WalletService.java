package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationDtoReq;

@Service
public interface WalletService {

	public List<WalletModel> getAllWallets();

	public Optional<WalletModel> getWalletByUserId(String userId);
	
	public Optional<WalletModel> getWalletById(String walletId);
	
	public WalletModel createWallet(WalletCreationDtoReq wallet);
	
}
