package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.FixedTransactionModel;
import br.com.pucc.projetointegradorvi.models.VariableTransactionModel;
import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionReqDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationDto;
import br.com.pucc.projetointegradorvi.models.dto.VariableTransactionDto;
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

	public FixedTransactionModel createWalletFixedTransaction(String walletId, FixedTransactionReqDto ftransaction);

	public Optional<FixedTransactionModel> deleteWalletFixedTransaction(String walletId, String ftId);

	public VariableTransactionModel createWalletVariableTransaction(String walletId,
			VariableTransactionDto vtransaction);

	public Optional<VariableTransactionModel> deleteWalletVariableTransaction(String walletId, String vtId);

	public List<FixedTransactionDto> getWalletFixedTransaction(String walletId, Optional<String> startAt);

	public List<FixedTransactionByInstitutionDto> getWalletFixedTransactionByInstitution(String walletId,
			Optional<String> startAt);

	public FixedTransactionWithVariationDto getWalletFixedTransactionWithVariation(String walletId,
			Optional<String> startAt);

	public List<FixedTransactionWithVariationByInstitutionDto> getWalletFixedTransactionWithVariationByInstitution(
			String walletId, Optional<String> startAt);
}
