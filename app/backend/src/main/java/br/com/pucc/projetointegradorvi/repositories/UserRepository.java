package br.com.pucc.projetointegradorvi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    // Aqui você pode adicionar consultas personalizadas, se necessário
}

