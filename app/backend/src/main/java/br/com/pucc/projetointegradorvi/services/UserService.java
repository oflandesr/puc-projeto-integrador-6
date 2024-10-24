package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationReqDto;

@Service
public interface UserService {

	public List<UserModel> getAllUsers();

	public Optional<UserModel> getUserByLogin(String login);

	public UserModel createUser(UserCreationReqDto user);

	public Optional<UserModel> getUserById(String userId);

}
