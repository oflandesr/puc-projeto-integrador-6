package br.com.pucc.projetointegradorvi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthControler {
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String userValidation() {
		return "User, Successfully logged in!";
	}
}
