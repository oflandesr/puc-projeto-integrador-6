package br.com.pucc.projetointegradorvi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pucc.projetointegradorvi.models.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
	public Optional<RoleModel> findByRole(String role);
}
