package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.UserModel2;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq2;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes2;

@Service
public interface UserService {
	
	public List<UserModel> getAllUsers();
	
	public Optional<UserModel> getUserByLogin(String login);

	public UserCreationDtoRes createUser(UserCreationDtoReq user);

	public UserModel updateUser(UserModel user);

	public void deleteUser(UserModel user);

	
	public List<UserModel2> getAllUsers2();
	
	public Optional<UserModel2> getUserByLogin2(String login);

	public UserCreationDtoRes2 createUser2(UserCreationDtoReq2 user);
	
	public Optional<UserModel2> getUserById2(String userId);
	
}
