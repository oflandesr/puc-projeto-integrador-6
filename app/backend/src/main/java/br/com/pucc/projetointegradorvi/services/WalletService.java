package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.FixedTransactionModel;
import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationResDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletReqDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletUpdateResDto;

@Service
public interface WalletService {

	public List<WalletDto> getWallet(Optional<String> userId, Optional<String> walletId);
	
	public Optional<WalletModel> getWalletById(String walletId);
	
	public WalletCreationResDto createWallet(WalletReqDto wallet);
	
	public WalletUpdateResDto updateWallet(String walletId, WalletReqDto wallet);
	
	public Optional<WalletModel> deleteWallet(String walletId);
	
	public FixedTransactionModel createWalletFixedTransaction(String walletId, FixedTransactionDto ftransaction);
	
	public Optional<FixedTransactionModel> deleteWalletFixedTransaction(String walletId, String ftId);
}
