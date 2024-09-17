package br.com.pucc.projetointegradorvi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucc.projetointegradorvi.models.UserModel;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<UserModel> create(@RequestBody UserModel user) {
		return ResponseEntity.status(200).body(UserModel.getMockUser());
	}
	
}
