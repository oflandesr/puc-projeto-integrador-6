package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationResDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletReqDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletUpdateResDto;

@Service
public interface WalletService {

	public List<WalletModel> getAllWallets();

	public List<WalletModel> getWalletByUserId(String userId);
	
	public Optional<WalletModel> getWalletById(String walletId);
	
	public List<WalletModel> getWalletByUserIdAndWalletId(String userId, String walletId);
	
	public WalletCreationResDto createWallet(WalletReqDto wallet);
	
	public WalletUpdateResDto updateWallet(String walletId, WalletReqDto wallet);
	
	public Optional<WalletModel> deleteWallet(String walletId);
}
