package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.UserModel;
import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationDtoReq;
import br.com.pucc.projetointegradorvi.models.dto.WalletUpdateDtoReq;
import br.com.pucc.projetointegradorvi.repositories.UserRepository2;
import br.com.pucc.projetointegradorvi.repositories.WalletRepository;

@Service
public class WalletServiceImp implements WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private UserRepository2 userRepository2;

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
	public WalletModel createWallet(WalletCreationDtoReq wallet) {

		// Buscar o user no banco de dados
		Optional<UserModel> um = userRepository2.findById(Long.valueOf(wallet.getUser()));

		if (um.isPresent()) {

			WalletModel wm = new WalletModel(wallet.getName(), wallet.getObjective(), wallet.getIntenFixIncPercent(),
					wallet.getIntenStockPercent(), wallet.getIntenFilPercent(), um.get());

			return walletRepository.saveAndFlush(wm);

		} else {
			throw new RuntimeException("User not found with id: " + wallet.getUser());
		}
	}

	@Override
	public WalletModel updateWallet(String walletId, WalletUpdateDtoReq wallet) {
		Optional<WalletModel> optionalWallet = walletRepository.findById(Long.valueOf(walletId));

		if (optionalWallet.isPresent()) {
			WalletModel w = optionalWallet.get();
			w.setName(wallet.getName());
			return walletRepository.save(w);
		} else {
			// Trate o caso em que a carteira não é encontrada
			throw new RuntimeException("Wallet not found with id: " + walletId);
		}
	}

}
