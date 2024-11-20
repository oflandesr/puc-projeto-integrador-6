package br.com.pucc.projetointegradorvi.repositories;

import java.time.LocalDate;
import java.util.List;

import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationDto;
import br.com.pucc.projetointegradorvi.models.dto.VariableTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.VariableTransactionResumeDto;

public interface WalletQueriedRepository {

	// FIXED TRANSACTION
	public List<FixedTransactionDto> findWalletFixedTransaction(Long walletId, LocalDate currentDate);

	public List<FixedTransactionByInstitutionDto> findWalletFixedTransactionsByInstitution(Long walletId,
			LocalDate currentDate);

	public FixedTransactionWithVariationDto findWalletFixedTransactionWithVariation(Long walletId,
			LocalDate currentDate);

	public List<FixedTransactionWithVariationByInstitutionDto> findWalletFixedTransactionWithVariationByInstitution(
			Long walletId, LocalDate currentDate);

	// VARIABLE TRANSACTION
	public List<VariableTransactionDto> findWalletVariableTransaction(Long walletId, LocalDate startDate,
			LocalDate endDate);

	public VariableTransactionResumeDto findWalletVariableTransactionResume(Long walletId, LocalDate startDate,
			LocalDate endDate);

	public List<VariableTransactionDto> findWalletVariableTransactionWithVariation(Long walletId, LocalDate endDate);

	public VariableTransactionResumeDto findWalletVariableTransactionResumeWithVariation(Long walletId,
			LocalDate endDate);
}
