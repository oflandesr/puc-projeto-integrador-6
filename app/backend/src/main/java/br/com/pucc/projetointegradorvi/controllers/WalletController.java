package br.com.pucc.projetointegradorvi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationResDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletReqDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletUpdateResDto;
import br.com.pucc.projetointegradorvi.services.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private WalletService walletService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<WalletModel>> listWallet(@RequestParam("walletId") Optional<String> walletId,
			@RequestParam("userId") Optional<String> userId) {

		List<WalletModel> walletsList = List.of();

		if (walletId.isPresent() && userId.isPresent()) {
			List<WalletModel> um = this.walletService.getWalletByUserIdAndWalletId(userId.get(), walletId.get());
			walletsList = um;
		} else if (userId.isPresent()) {
			List<WalletModel> um = this.walletService.getWalletByUserId(userId.get());
			walletsList = um;
		} else if (walletId.isPresent()) {
			Optional<WalletModel> um = this.walletService.getWalletById(walletId.get());
			if (um.isPresent()) {
				walletsList = List.of(um.get());
			}
		} else {

			walletsList = this.walletService.getAllWallets();
		}

		return new ResponseEntity<List<WalletModel>>(walletsList, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<WalletCreationResDto> createWallet(@RequestBody WalletReqDto wallet) {
		return new ResponseEntity<WalletCreationResDto>(walletService.createWallet(wallet), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{walletId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<WalletModel> detailWallet(@PathVariable("walletId") String walletId) {

		Optional<WalletModel> wm = this.walletService.getWalletById(walletId);
		if (wm.isPresent()) {
			return new ResponseEntity<WalletModel>(wm.get(), HttpStatus.OK);
		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found");
	}

	@RequestMapping(value = "/{walletId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> removeWallet(@PathVariable("walletId") String walletId) {

		Optional<WalletModel> wm = this.walletService.deleteWallet(walletId);
		if (wm.isPresent()) {
			return ResponseEntity.accepted().build();
		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found");
	}

	@RequestMapping(value = "/{walletId}", method = RequestMethod.PATCH, produces = "application/json")
	public ResponseEntity<WalletUpdateResDto> updateWallet(@PathVariable("walletId") String walletId,
			@RequestBody WalletReqDto wallet) {
		return new ResponseEntity<WalletUpdateResDto>(this.walletService.updateWallet(walletId, wallet), HttpStatus.OK);
	}

}
