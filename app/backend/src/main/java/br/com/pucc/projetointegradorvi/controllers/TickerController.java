package br.com.pucc.projetointegradorvi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucc.projetointegradorvi.models.TickerModel;
import br.com.pucc.projetointegradorvi.services.TickerService;

@RestController
@RequestMapping("/ticker")
public class TickerController {

	@Autowired
	private TickerService tickerService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<TickerModel>> listTicker(@RequestParam("id") Optional<String> id) {
		return new ResponseEntity<List<TickerModel>>(tickerService.getTicker(id), HttpStatus.OK);
	}
}