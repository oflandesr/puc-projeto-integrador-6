package br.com.pucc.projetointegradorvi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pucc.projetointegradorvi.models.UserModel2;

@Repository
public interface UserRepository2 extends JpaRepository<UserModel2, Long> {
	public Optional<UserModel2> findByLogin(String login);
	public Optional<UserModel2> findById(Long id);
}
