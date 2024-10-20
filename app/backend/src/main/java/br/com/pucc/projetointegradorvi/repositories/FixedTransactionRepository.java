package br.com.pucc.projetointegradorvi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pucc.projetointegradorvi.models.FixedTransactionModel;

@Repository
public interface FixedTransactionRepository extends JpaRepository<FixedTransactionModel, Long> {

	public List<FixedTransactionModel> findByWalletId(Long walletId);

	//public List<WalletModel> findByUserIdAndId(Long userId, Long waletId);
}
