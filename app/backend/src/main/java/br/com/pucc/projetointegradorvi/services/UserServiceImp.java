package br.com.pucc.projetointegradorvi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.RoleModel;
import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationReqDto;
import br.com.pucc.projetointegradorvi.repositories.RoleRepository;
import br.com.pucc.projetointegradorvi.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService {

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<UserModel> getAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public UserModel createUser(UserCreationReqDto req) {

		// Buscar a role "USER" no banco de dados
		Optional<RoleModel> userRoleOptional = roleRepository.findByRole("USER");

		if (userRoleOptional.isEmpty()) {
			throw new RuntimeException("Role USER não encontrada no banco de dados");
		}

		RoleModel userRole = userRoleOptional.get();

		Set<RoleModel> roles = new HashSet<>();
		roles.add(userRole);

		UserModel user = new UserModel(req.getLogin(), encoder.encode(req.getPassword()), req.getFirstName(),
				req.getLastName(), roles);

		return this.userRepository.saveAndFlush(user);

	}

	@Override
	public Optional<UserModel> getUserByLogin(String login) {
		return this.userRepository.findByLogin(login);
	}

	@Override
	public Optional<UserModel> getUserById(String userId) {
		return this.userRepository.findById(Long.valueOf(userId));
	}

	@Override
	public List<UserModel> getUser(Optional<String> id, Optional<String> login) {

		List<UserModel> res = new ArrayList<UserModel>();

		if (id.isPresent() && login.isPresent()) {
			Optional<UserModel> um = this.userRepository.findByIdAndLogin(Long.valueOf(id.get()), login.get());
			if (um.isPresent()) {
				res.add(um.get());
			}
		} else if (id.isPresent()) {
			Optional<UserModel> um = this.userRepository.findById(Long.valueOf(id.get()));
			if (um.isPresent()) {
				res.add(um.get());
			}
		} else if (login.isPresent()) {
			Optional<UserModel> um = this.userRepository.findByLogin(login.get());
			if (um.isPresent()) {
				res.add(um.get());
			}
		} else {

			res = this.userRepository.findAll();
		}

		return res;
	}

}
