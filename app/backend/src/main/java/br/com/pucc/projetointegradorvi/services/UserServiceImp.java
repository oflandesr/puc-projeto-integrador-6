package br.com.pucc.projetointegradorvi.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.LoginModel;
import br.com.pucc.projetointegradorvi.models.RoleModel;
import br.com.pucc.projetointegradorvi.models.RoleModel2;
import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.UserModel2;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq2;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes2;
import br.com.pucc.projetointegradorvi.repositories.RoleRepository;
import br.com.pucc.projetointegradorvi.repositories.RoleRepository2;
import br.com.pucc.projetointegradorvi.repositories.UserRepository;
import br.com.pucc.projetointegradorvi.repositories.UserRepository2;

@Service
public class UserServiceImp implements UserService {

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRepository2 userRepository2;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleRepository2 roleRepository2;

	@Override
	public List<UserModel> getAllUsers() {
		return this.userRepository.findAll();
	}
	
	@Override
	public List<UserModel2> getAllUsers2() {
		return this.userRepository2.findAll();
	}

	@Override
	public UserCreationDtoRes createUser(UserCreationDtoReq req) {

		// Buscar a role "USER" no banco de dados
		Optional<RoleModel> userRoleOptional = roleRepository.findByRole("USER");

		if (userRoleOptional.isEmpty()) {
			throw new RuntimeException("Role USER não encontrada no banco de dados");
		}

		RoleModel userRole = userRoleOptional.get();

		// Criar o login com a role associada
		Set<RoleModel> roles = new HashSet<>();
		roles.add(userRole);

		LoginModel login = new LoginModel(req.getAcesso().getLogin(), req.getAcesso().getPassword(), roles);
		UserModel user = new UserModel(req.getFirstName(), req.getLastName(), login);
		UserModel saved = this.userRepository.saveAndFlush(user);

		UserCreationDtoRes res = new UserCreationDtoRes();
		res.setUser(saved);

		return res;
	}

	@Override
	public UserCreationDtoRes2 createUser2(UserCreationDtoReq2 req) {

		// Buscar a role "USER" no banco de dados
		Optional<RoleModel2> userRoleOptional = roleRepository2.findByRole("USER");

		if (userRoleOptional.isEmpty()) {
			throw new RuntimeException("Role USER não encontrada no banco de dados");
		}

		RoleModel2 userRole = userRoleOptional.get();

		Set<RoleModel2> roles = new HashSet<>();
		roles.add(userRole);

		UserModel2 user = new UserModel2(req.getLogin(), encoder.encode(req.getPassword()), req.getFirstName(),
				req.getLastName(), roles);
		UserModel2 saved = this.userRepository2.saveAndFlush(user);

		UserCreationDtoRes2 res = new UserCreationDtoRes2();
		res.setUser(saved);

		return res;
	}

	@Override
	public Optional<UserModel> getUserByLogin(String login) {
		return this.userRepository.findByAccessLogin(login);
	}
	
	@Override
	public Optional<UserModel2> getUserByLogin2(String login) {
		return this.userRepository2.findByLogin(login);
	}

	@Override
	public  Optional<UserModel2> getUserById2(String userId) {
		return this.userRepository2.findById(Long.valueOf(userId));
	}
	
	@Override
	public UserModel updateUser(UserModel user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(UserModel user) {
		// TODO Auto-generated method stub
		
	}

}
