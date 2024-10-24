package br.com.pucc.projetointegradorvi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	public final Optional<UserModel> getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return this.userRepository.findByLogin(authentication.getName());
		}
		throw new RuntimeException("Falha na identificação do usuário autenticado usando login: " + authentication.getName());
	}
}
