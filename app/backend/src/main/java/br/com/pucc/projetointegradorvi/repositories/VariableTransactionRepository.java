package br.com.pucc.projetointegradorvi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pucc.projetointegradorvi.models.VariableTransactionModel;

public interface VariableTransactionRepository extends JpaRepository<VariableTransactionModel, Long> {

}
