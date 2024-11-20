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
import br.com.pucc.projetointegradorvi.models.VariableTransactionModel;
import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionReqDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationDto;
import br.com.pucc.projetointegradorvi.models.dto.VariableTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.VariableTransactionReqDto;
import br.com.pucc.projetointegradorvi.models.dto.VariableTransactionResumeDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationResDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletReqDto;
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
	public ResponseEntity<FixedTransactionModel> createWalletFixedTransaction(@PathVariable("walletId") String walletId,
			@RequestBody FixedTransactionReqDto wallet) {

		return new ResponseEntity<FixedTransactionModel>(
				this.walletService.createWalletFixedTransaction(walletId, wallet), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{walletId}/tfixed", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<FixedTransactionDto>> listWalletFixedTransaction(
			@PathVariable("walletId") String walletId, @RequestParam("endDate") Optional<String> endDate) {

		return new ResponseEntity<List<FixedTransactionDto>>(
				this.walletService.getWalletFixedTransaction(walletId, endDate), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tfixed/institution", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<FixedTransactionByInstitutionDto>> listWalletFixedTransactionByInstitution(
			@PathVariable("walletId") String walletId, @RequestParam("endDate") Optional<String> endDate) {

		return new ResponseEntity<List<FixedTransactionByInstitutionDto>>(
				this.walletService.getWalletFixedTransactionByInstitution(walletId, endDate), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tfixed/variation", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<FixedTransactionWithVariationDto> listWalletFixedTransactionWithVariation(
			@PathVariable("walletId") String walletId, @RequestParam("endDate") Optional<String> endDate) {

		return new ResponseEntity<FixedTransactionWithVariationDto>(
				this.walletService.getWalletFixedTransactionWithVariation(walletId, endDate), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tfixed/variation/institution", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<FixedTransactionWithVariationByInstitutionDto>> listWalletFixedTransactionWithVariationByInstitution(
			@PathVariable("walletId") String walletId, @RequestParam("endDate") Optional<String> endDate) {

		return new ResponseEntity<List<FixedTransactionWithVariationByInstitutionDto>>(
				this.walletService.getWalletFixedTransactionWithVariationByInstitution(walletId, endDate),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tfixed/{tfId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> deleteWalletFixedTransaction(@PathVariable("walletId") String walletId,
			@PathVariable("tfId") String tfId) {
		Optional<FixedTransactionModel> ft = this.walletService.deleteWalletFixedTransaction(walletId, tfId);
		if (ft.isPresent()) {
			return ResponseEntity.accepted().build();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet fixed transaction not found");
	}

	// VARIABLE TRANSACTION
	@RequestMapping(value = "/{walletId}/tvariable", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<VariableTransactionModel> createWalletVariableTransaction(
			@PathVariable("walletId") String walletId, @RequestBody VariableTransactionReqDto wallet) {

		return new ResponseEntity<VariableTransactionModel>(
				this.walletService.createWalletVariableTransaction(walletId, wallet), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{walletId}/tvariable", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<VariableTransactionDto>> listWalletVariableTransaction(
			@PathVariable("walletId") String walletId, @RequestParam("startAt") Optional<String> startAt,
			@RequestParam("endAt") Optional<String> endAt) {

		return new ResponseEntity<List<VariableTransactionDto>>(
				this.walletService.getWalletVariableTransaction(walletId, startAt, endAt), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tvariable/resume", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<VariableTransactionResumeDto> listWalletVariableTransactionResume(
			@PathVariable("walletId") String walletId, @RequestParam("startAt") Optional<String> startAt,
			@RequestParam("endAt") Optional<String> endAt) {

		return new ResponseEntity<VariableTransactionResumeDto>(
				this.walletService.getWalletVariableTransactionResume(walletId, startAt, endAt), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tvariable/variation", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<VariableTransactionDto>> listWalletVariableTransactionWithVariation(
			@PathVariable("walletId") String walletId, @RequestParam("endAt") Optional<String> endAt) {

		return new ResponseEntity<List<VariableTransactionDto>>(
				this.walletService.getWalletVariableTransactionWithVariation(walletId, endAt), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tvariable/resume/variation", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<VariableTransactionResumeDto> listWalletVariableTransactionResumeWithVariation(
			@PathVariable("walletId") String walletId, @RequestParam("endAt") Optional<String> endAt) {

		return new ResponseEntity<VariableTransactionResumeDto>(
				this.walletService.getWalletVariableTransactionResumeWithVariation(walletId, endAt), HttpStatus.OK);
	}

	@RequestMapping(value = "/{walletId}/tvariable/{tvId}", method = RequestMethod.DELETE, produces = "application/json")

	public ResponseEntity<Void> deleteWalletVariableTransaction(@PathVariable("walletId") String walletId,
			@PathVariable("tvId") String tvId) {
		Optional<VariableTransactionModel> vt = this.walletService.deleteWalletVariableTransaction(walletId, tvId);
		if (vt.isPresent()) {
			return ResponseEntity.accepted().build();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet variable transaction not found");
	}
}
