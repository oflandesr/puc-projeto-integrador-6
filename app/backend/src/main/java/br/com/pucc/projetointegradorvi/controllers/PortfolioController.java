package br.com.pucc.projetointegradorvi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucc.projetointegradorvi.models.PortfolioModel;

@RestController
@RequestMapping("/portifolio")
public class PortfolioController {
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PortfolioModel> getPortfolio() {
		return ResponseEntity.status(200).body(PortfolioModel.getMockPortfolio());
	}
	
}
