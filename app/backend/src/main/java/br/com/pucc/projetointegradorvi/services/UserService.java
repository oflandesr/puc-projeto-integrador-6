package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes;

@Service
public interface UserService {
	
	public List<UserModel> getAllUsers();
	
	public Optional<UserModel> getUserByLogin(String login);

	public UserCreationDtoRes createUser(UserCreationDtoReq user);

	public UserModel updateUser(UserModel user);

	public void deleteUser(UserModel user);
	
}
