package br.com.pucc.projetointegradorvi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.dto.ResponseReturnDto;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes;
import br.com.pucc.projetointegradorvi.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<UserModel>> getUsers() {

		List<UserModel> usersList = this.userService.getAllUsers();

		return new ResponseEntity<List<UserModel>>(usersList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{login}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserByLogin(@PathVariable("login") String login) {

		Optional<UserModel> user = userService.getUserByLogin(login);

		if (user.isPresent()) {
			return ResponseEntity.status(200).body(user.get());
		} else {
			return ResponseEntity.status(404).body(new ResponseReturnDto("404", "404_Not_Found", "Login não encontrado!"));
		}

	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<UserCreationDtoRes> create(@RequestBody UserCreationDtoReq user) {
		return ResponseEntity.status(200).body(userService.createUser(user));
	}
}
