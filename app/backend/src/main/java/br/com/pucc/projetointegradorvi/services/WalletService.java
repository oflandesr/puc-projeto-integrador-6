package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.WalletUpdateDtoReq;

@Service
public interface WalletService {

	public List<WalletModel> getAllWallets();

	public List<WalletModel> getWalletByUserId(String userId);
	
	public Optional<WalletModel> getWalletById(String walletId);
	
	public List<WalletModel> getWalletByUserIdAndWalletId(String userId, String walletId);
	
	public WalletModel createWallet(WalletCreationDtoReq wallet);
	
	public WalletModel updateWallet(String walletId, WalletUpdateDtoReq wallet);
	
	
}
