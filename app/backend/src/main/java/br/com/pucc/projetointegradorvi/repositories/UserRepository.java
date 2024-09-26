package br.com.pucc.projetointegradorvi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pucc.projetointegradorvi.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	public UserModel findUserByLoginId(Integer userLoginId);
}
