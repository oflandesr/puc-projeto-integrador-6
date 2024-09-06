package br.com.pucc.projetointegradorvi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucc.projetointegradorvi.models.StockDetailsModel;
import br.com.pucc.projetointegradorvi.models.StockModel;

@RestController
@RequestMapping("/stocks")
public class StocksController {

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<StockModel>> getStocks() {
		return ResponseEntity.status(200).body(List.of(StockModel.getMockStockModel()));
	}
	
	@RequestMapping(value = "/{stockId}/details", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<StockDetailsModel> getStockDetails(@PathVariable("stockId") String stockId) {
		return ResponseEntity.status(200).body(StockDetailsModel.getMockStockDetailsModel());
	}
}
