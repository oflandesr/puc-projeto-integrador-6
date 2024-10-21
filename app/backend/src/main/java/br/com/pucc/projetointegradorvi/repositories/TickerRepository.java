package br.com.pucc.projetointegradorvi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pucc.projetointegradorvi.models.TickerModel;

public interface TickerRepository extends JpaRepository<TickerModel, String> {

}
