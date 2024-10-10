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

import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationDtoReq;
import br.com.pucc.projetointegradorvi.services.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private WalletService walletService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<WalletModel>> getWallet(@RequestParam("walletId") Optional<String> walletId,
			@RequestParam("userId") Optional<String> userId) {

		List<WalletModel> walletsList = List.of();

		if (walletId.isPresent() && userId.isPresent()) {
			// String u = userId.get();
			// String lString = login.get();
		} else if (userId.isPresent()) {
			String u = userId.get();
			Optional<WalletModel> um = this.walletService.getWalletByUserId(u);
			if (um.isPresent()) {
				walletsList = List.of(um.get());
			}
		} else if (walletId.isPresent()) {
			String w = walletId.get();
			Optional<WalletModel> um = this.walletService.getWalletById(w);
			if (um.isPresent()) {
				walletsList = List.of(um.get());
			}
		} else {

			walletsList = this.walletService.getAllWallets();
		}

		return new ResponseEntity<List<WalletModel>>(walletsList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<WalletModel> create(@RequestBody WalletCreationDtoReq wallet) {
		return ResponseEntity.status(200).body(walletService.createWallet(wallet));
	}
}
