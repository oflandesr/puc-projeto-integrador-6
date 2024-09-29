package br.com.pucc.projetointegradorvi.services;

import java.util.HashSet;
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
import br.com.pucc.projetointegradorvi.repositories.RoleRepository;
import br.com.pucc.projetointegradorvi.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<UserModel> getAllUsers() {
		return this.userRepository.findAll();
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
	public UserModel updateUser(UserModel user) {
		return this.userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteUser(UserModel user) {
		this.userRepository.delete(user);
	}

	@Override
	public Optional<UserModel> getUserByLogin(String login) {
		
		return this.userRepository.findByAccessLogin(login);
	}

}
