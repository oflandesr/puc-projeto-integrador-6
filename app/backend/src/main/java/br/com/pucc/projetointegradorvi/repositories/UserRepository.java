package br.com.pucc.projetointegradorvi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pucc.projetointegradorvi.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	public Optional<UserModel> findByLogin(String login);
	public Optional<UserModel> findById(Long id);
}
