package br.com.pucc.projetointegradorvi.repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pucc.projetointegradorvi.models.IndexModel;

@Repository
public interface IndexRepository  extends JpaRepository<IndexModel, Date> {
	
}
