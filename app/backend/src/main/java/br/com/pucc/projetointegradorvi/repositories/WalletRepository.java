package br.com.pucc.projetointegradorvi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pucc.projetointegradorvi.models.WalletModel;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel, Long> {
	
	public List<WalletModel> findByUserId(Long userId);
	
	public List<WalletModel> findByUserIdAndId(Long userId, Long waletId);
	
}