package br.com.pucc.projetointegradorvi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.UserModel;

@Service
public interface UserService {
	// GET UserS
	public List<UserModel> getAllUsers();
	public UserModel getUserById(Integer userId);
	public UserModel getUserByLoginId(Integer userLoginId);
	// CREATE UserS
	public UserModel createUser(UserModel user);
	// UPDATE UserS
	public UserModel updateUser(UserModel user);
	// DELETE UserS
	public void deleteUser(UserModel user);
}

