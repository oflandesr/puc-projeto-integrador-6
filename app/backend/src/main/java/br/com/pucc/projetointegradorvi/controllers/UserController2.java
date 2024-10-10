package br.com.pucc.projetointegradorvi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucc.projetointegradorvi.models.UserModel2;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoReq2;
import br.com.pucc.projetointegradorvi.models.dto.UserCreationDtoRes2;
import br.com.pucc.projetointegradorvi.services.UserService;

@RestController
@RequestMapping("/v2/user")
public class UserController2 {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<UserModel2>> getUser(@RequestParam("userId") Optional<String> userId,
			@RequestParam("login") Optional<String> login) {

		List<UserModel2> usersList = List.of();

		if (userId.isPresent() && login.isPresent()) {
			// String u = userId.get();
			// String lString = login.get();
		} else if (userId.isPresent()) {
			String u = userId.get();
			Optional<UserModel2> um = this.userService.getUserById2(u);
			if (um.isPresent()) {
				usersList = List.of(um.get());
			}
		} else if (login.isPresent()) {
			String l = login.get();
			Optional<UserModel2> um = this.userService.getUserByLogin2(l);
			if (um.isPresent()) {
				usersList = List.of(um.get());
			}
		} else {

			usersList = this.userService.getAllUsers2();
		}

		return new ResponseEntity<List<UserModel2>>(usersList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<UserCreationDtoRes2> create(@RequestBody UserCreationDtoReq2 user) {
		return ResponseEntity.status(200).body(userService.createUser2(user));
	}
}
