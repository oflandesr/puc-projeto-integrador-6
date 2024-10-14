package br.com.pucc.projetointegradorvi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthControler {

	@Autowired
	private AuthService authService;

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public ResponseEntity<UserModel> userValidation() {

		Optional<UserModel> user = this.authService.getCurrentUser();
		if (user.isPresent()) {
			return new ResponseEntity<UserModel>(user.get(), HttpStatus.OK);
		}
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized");
	}
}
