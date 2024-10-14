package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationResDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletReqDto;
import br.com.pucc.projetointegradorvi.models.dto.WalletUpdateResDto;
import br.com.pucc.projetointegradorvi.repositories.WalletRepository;

@Service
public class WalletServiceImp implements WalletService {

	@Autowired
	private AuthService authService;

	@Autowired
	private WalletRepository walletRepository;

	@Override
	public List<WalletModel> getAllWallets() {
		return walletRepository.findAll();
	}

	@Override
	public List<WalletModel> getWalletByUserId(String userId) {
		return walletRepository.findByUserId(Long.valueOf(userId));
	}

	@Override
	public Optional<WalletModel> getWalletById(String walletId) {
		return walletRepository.findById(Long.valueOf(walletId));
	}

	@Override
	public List<WalletModel> getWalletByUserIdAndWalletId(String userId, String walletId) {
		return walletRepository.findByUserIdAndId(Long.valueOf(userId), Long.valueOf(walletId));
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
		} else {
			// Trate o caso em que a carteira não é encontrada
			throw new RuntimeException("Wallet not found with id: " + walletId);
		}
	}

	@Override
	public Optional<WalletModel> deleteWallet(String walletId) {
		Optional<WalletModel> wm = this.walletRepository.findById(Long.valueOf(walletId));
		if (wm.isPresent()) {
			walletRepository.delete(wm.get());
		}
		return wm;
	}

}
