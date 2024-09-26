package br.com.pucc.projetointegradorvi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;


	@RequestMapping(path = "/users", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<UserModel>> getUsers() {

		List<UserModel> usersList = this.userService.getAllUsers();

		return new ResponseEntity<List<UserModel>>(usersList, HttpStatus.OK);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<UserModel> create(@RequestBody UserModel user) {
		return ResponseEntity.status(200).body(UserModel.getMockUser());
	}

}
