package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes;

@Service
public interface UserService {

	public List<UserModel> getAllUsers2();

	public Optional<UserModel> getUserByLogin2(String login);

	public UserCreationDtoRes createUser2(UserCreationDtoReq user);

	public Optional<UserModel> getUserById2(String userId);

}
