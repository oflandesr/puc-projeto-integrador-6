package br.com.pucc.projetointegradorvi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.pucc.projetointegradorvi.models.FixedTransactionModel;
import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationResDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletReqDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletUpdateResDto;
import br.com.pucc.projetointegradorvi.repositories.FixedTransactionRepository;
import br.com.pucc.projetointegradorvi.repositories.WalletRepository;

@Service
public class WalletServiceImp implements WalletService {

	@Autowired
	private AuthService authService;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private FixedTransactionRepository ftRepository;

	@Override
	public List<WalletDto> getWallet(Optional<String> userId, Optional<String> walletId) {

		List<WalletDto> walletList = new ArrayList<WalletDto>();

		if (walletId.isPresent() && userId.isPresent()) {

			Optional<WalletModel> w = this.walletRepository.findByUserIdAndId(Long.valueOf(userId.get()),
					Long.valueOf(walletId.get()));
			if (w.isPresent()) {
				walletList.add(w.get().convertToDto());
			}

		} else if (userId.isPresent()) {

			List<WalletModel> w = this.walletRepository.findByUserId(Long.valueOf(userId.get()));
			w.forEach(wd -> walletList.add(wd.convertToDto()));

		} else if (walletId.isPresent()) {

			Optional<WalletModel> w = this.walletRepository.findById(Long.valueOf(walletId.get()));
			if (w.isPresent()) {
				walletList.add(w.get().convertToDto());
			}

		} else {

			List<WalletModel> w = this.walletRepository.findAll();
			w.forEach(wd -> walletList.add(wd.convertToDto()));

		}

		return walletList;
	}

	@Override
	public Optional<WalletModel> getWalletById(String walletId) {
		return walletRepository.findById(Long.valueOf(walletId));
	}

	@Override
	public WalletCreationResDto createWallet(WalletReqDto wallet) {

		// Buscar o user no banco de dados
		Optional<UserModel> um = this.authService.getCurrentUser();

		if (um.isPresent()) {

			WalletModel wm = new WalletModel(wallet.getName(), wallet.getObjective(),
					(String) wallet.getIntenFixIncPercent(), (String) wallet.getIntenStockPercent(),
					(String) wallet.getIntenFilPercent(), um.get());
			wm = walletRepository.saveAndFlush(wm);

			return new WalletCreationResDto(wm);

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}

	}

	@Override
	public WalletUpdateResDto updateWallet(String walletId, WalletReqDto wallet) {
		Optional<WalletModel> optionalWallet = walletRepository.findById(Long.valueOf(walletId));

		if (optionalWallet.isPresent()) {
			WalletModel w = optionalWallet.get();
			if (wallet.getName() != null)
				w.setName(wallet.getName());
			if (wallet.getObjective() != null)
				w.setObjective(wallet.getObjective());
			if (wallet.getIntenFixIncPercent() != null)
				w.setIntenFixIncPercent(Integer.valueOf(wallet.getIntenFixIncPercent()));
			if (wallet.getIntenStockPercent() != null)
				w.setIntenStockPercent(Integer.valueOf(wallet.getIntenStockPercent()));
			if (wallet.getIntenFilPercent() != null)
				w.setIntenFilPercent(Integer.valueOf(wallet.getIntenFilPercent()));

			return new WalletUpdateResDto(this.walletRepository.save(w));
		}
		throw new RuntimeException("Wallet not found with id: " + walletId);

	}

	@Override
	public Optional<WalletModel> deleteWallet(String walletId) {
		Optional<WalletModel> wm = this.walletRepository.findById(Long.valueOf(walletId));
		if (wm.isPresent()) {
			walletRepository.delete(wm.get());
		}
		return wm;
	}

	@Override
	public FixedTransactionModel createWalletFixedTransaction(String walletId, FixedTransactionDto ftransaction) {
		Optional<WalletModel> optionalWallet = walletRepository.findById(Long.valueOf(walletId));

		if (optionalWallet.isPresent()) {
			WalletModel w = optionalWallet.get();
			FixedTransactionModel ftm = new FixedTransactionModel(w, ftransaction.getInstitution(),
					ftransaction.getType(), ftransaction.getValue(), ftransaction.getStartDate(),
					ftransaction.getEndDate(), ftransaction.getIndexName(), ftransaction.getValue());
			return this.ftRepository.saveAndFlush(ftm);
		}

		throw new RuntimeException("Wallet not found with id: " + walletId);
	}

	@Override
	public Optional<FixedTransactionModel> deleteWalletFixedTransaction(String walletId, String ftId) {
		Optional<WalletModel> w = walletRepository.findById(Long.valueOf(walletId));
		if (w.isPresent()) {
			Optional<FixedTransactionModel> f = this.ftRepository.findById(Long.valueOf(ftId));
			if (f.isPresent()) {
				this.ftRepository.delete(f.get());
				return f;
			} else {
				throw new RuntimeException("Fixed transaction not found with id: " + ftId);
			}
		} else {
			throw new RuntimeException("Wallet not found with id: " + walletId);
		}
	}

}
