package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.LoginModel;
import br.com.pucc.projetointegradorvi.models.RoleModel;
import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes;
import br.com.pucc.projetointegradorvi.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserModel> getAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public UserCreationDtoRes createUser(UserCreationDtoReq req) {
		
		LoginModel login = new LoginModel(req.getAcesso().getLogin(), req.getAcesso().getPassword(), Set.of(new RoleModel("USER")));
		UserModel user = new UserModel(req.getFirstName(), req.getLastName(), login);
		UserModel saved = this.userRepository.saveAndFlush(user);
		
		UserCreationDtoRes res = new UserCreationDtoRes();
		res.setUser(saved.getId().toString());
		
		return res;
	}

	@Override
	public UserModel updateUser(UserModel user) {
		return this.userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteUser(UserModel user) {
		this.userRepository.delete(user);
	}

	@Override
	public Optional<UserModel> getUserByLogin(String login) {
		
		return this.userRepository.findByAcessoLogin(login);
	}

}
