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

import br.com.pucc.projetointegradorvi.models.FixedTransactionModel;
import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationResDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletReqDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletUpdateResDto;
import br.com.pucc.projetointegradorvi.services.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private WalletService walletService;

	// WALLET
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<WalletDto>> listWallet(@RequestParam("walletId") Optional<String> walletId,
			@RequestParam("userId") Optional<String> userId) {
		return new ResponseEntity<List<WalletDto>>(walletService.getWallet(userId, walletId), HttpStatus.OK);
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

	// FIXED TRANSACTION
	@RequestMapping(value = "/{walletId}/tfixed", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> createWalletFixedTransaction(@PathVariable("walletId") String walletId,
			@RequestBody FixedTransactionDto wallet) {

		FixedTransactionModel wm = this.walletService.createWalletFixedTransaction(walletId, wallet);
		if (wm != null) {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found");
	}

	@RequestMapping(value = "/{walletId}/tfixed/{tfId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> deleteWalletFixedTransaction(@PathVariable("walletId") String walletId, @PathVariable("tfId") String tfId) {
		Optional<FixedTransactionModel> ft = this.walletService.deleteWalletFixedTransaction(walletId, tfId);
		if (ft.isPresent()) {
			return ResponseEntity.accepted().build();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet fixed transaciont not found");
	}

	@RequestMapping(value = "/{walletId}/tvariable", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<WalletUpdateResDto> createWalletVariableTransaction(@PathVariable("walletId") String walletId,
			@RequestBody WalletReqDto wallet) {
		return new ResponseEntity<WalletUpdateResDto>(this.walletService.updateWallet(walletId, wallet), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tvariable", method = RequestMethod.PATCH, produces = "application/json")
	public ResponseEntity<WalletUpdateResDto> updateWalletVariableTransaction(@PathVariable("walletId") String walletId,
			@RequestBody WalletReqDto wallet) {
		return new ResponseEntity<WalletUpdateResDto>(this.walletService.updateWallet(walletId, wallet), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tvariable", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<WalletUpdateResDto> deleteWalletVariableTransaction(@PathVariable("walletId") String walletId,
			@RequestBody WalletReqDto wallet) {
		return new ResponseEntity<WalletUpdateResDto>(this.walletService.updateWallet(walletId, wallet), HttpStatus.OK);
	}
}
