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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationReqDto;
import br.com.pucc.projetointegradorvi.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<UserModel>> listUsers(@RequestParam("userId") Optional<String> userId,
			@RequestParam("login") Optional<String> login) {

		List<UserModel> usersList = List.of();

		if (userId.isPresent() && login.isPresent()) {
			// String u = userId.get();
			// String lString = login.get();
		} else if (userId.isPresent()) {
			Optional<UserModel> um = this.userService.getUserById(userId.get());
			if (um.isPresent()) {
				usersList = List.of(um.get());
			}
		} else if (login.isPresent()) {
			Optional<UserModel> um = this.userService.getUserByLogin(login.get());
			if (um.isPresent()) {
				usersList = List.of(um.get());
			}
		} else {

			usersList = this.userService.getAllUsers();
		}

		return new ResponseEntity<List<UserModel>>(usersList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<UserModel> createUser(@RequestBody UserCreationReqDto user) {
		return new ResponseEntity<UserModel>(userService.createUser(user), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<UserModel> detailUser(@PathVariable("id") String id) {
		
		Optional<UserModel> um = this.userService.getUserById(id);
		if (um.isPresent()) {
			return new ResponseEntity<UserModel>(um.get(), HttpStatus.OK);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	}
}
