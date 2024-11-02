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

import br.com.pucc.projetointegradorvi.models.IndexModel;
import br.com.pucc.projetointegradorvi.services.IndexService;

@RestController
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private IndexService indexService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<IndexModel>> listIndexies(@RequestParam("date") Optional<String> date) {
		return new ResponseEntity<List<IndexModel>>(this.indexService.getIndex(date), HttpStatus.OK);
	}
}
