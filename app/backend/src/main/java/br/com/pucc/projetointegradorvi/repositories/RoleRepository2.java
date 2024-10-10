package br.com.pucc.projetointegradorvi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pucc.projetointegradorvi.models.RoleModel2;

@Repository
public interface RoleRepository2 extends JpaRepository<RoleModel2, Long> {
	public Optional<RoleModel2> findByRole(String role);
}
