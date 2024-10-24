package br.com.pucc.projetointegradorvi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucc.projetointegradorvi.models.TickerModel;
import br.com.pucc.projetointegradorvi.services.TickerService;

@RestController
@RequestMapping("/ticker")
public class TickerController {

	@Autowired
	private TickerService tickerService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<TickerModel>> listTicker() {

		return new ResponseEntity<List<TickerModel>>(tickerService.getTicker(), HttpStatus.OK);
	}
}