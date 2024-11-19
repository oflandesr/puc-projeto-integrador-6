package br.com.pucc.projetointegradorvi.repositories;

import java.time.LocalDate;
import java.util.List;

import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationDto;

public interface WalletQueriedRepository {

	public List<FixedTransactionDto> findFixedTransaction(Long walletId, LocalDate currentDate);

	public List<FixedTransactionByInstitutionDto> findFixedTransactionsByInstitution(Long walletId,
			LocalDate currentDate);

	public FixedTransactionWithVariationDto findFixedTransactionWithVariation(Long walletId, LocalDate currentDate);

	public List<FixedTransactionWithVariationByInstitutionDto> findFixedTransactionWithVariationByInstitution(
			Long walletId, LocalDate currentDate);
}
