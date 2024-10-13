package br.com.pucc.projetointegradorvi.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.RoleModel2;
import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes;
import br.com.pucc.projetointegradorvi.repositories.RoleRepository2;
import br.com.pucc.projetointegradorvi.repositories.UserRepository2;

@Service
public class UserServiceImp implements UserService {

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository2 userRepository2;

	@Autowired
	private RoleRepository2 roleRepository2;

	@Override
	public List<UserModel> getAllUsers2() {
		return this.userRepository2.findAll();
	}

	@Override
	public UserCreationDtoRes createUser2(UserCreationDtoReq req) {

		// Buscar a role "USER" no banco de dados
		Optional<RoleModel2> userRoleOptional = roleRepository2.findByRole("USER");

		if (userRoleOptional.isEmpty()) {
			throw new RuntimeException("Role USER não encontrada no banco de dados");
		}

		RoleModel2 userRole = userRoleOptional.get();

		Set<RoleModel2> roles = new HashSet<>();
		roles.add(userRole);

		UserModel user = new UserModel(req.getLogin(), encoder.encode(req.getPassword()), req.getFirstName(),
				req.getLastName(), roles);
		UserModel saved = this.userRepository2.saveAndFlush(user);

		UserCreationDtoRes res = new UserCreationDtoRes();
		res.setUser(saved);

		return res;
	}

	@Override
	public Optional<UserModel> getUserByLogin2(String login) {
		return this.userRepository2.findByLogin(login);
	}

	@Override
	public Optional<UserModel> getUserById2(String userId) {
		return this.userRepository2.findById(Long.valueOf(userId));
	}

}
