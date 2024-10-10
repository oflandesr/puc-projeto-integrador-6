package br.com.pucc.projetointegradorvi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pucc.projetointegradorvi.models.UserModel2;
import br.com.pucc.projetointegradorvi.models.WalletModel;
import br.com.pucc.projetointegradorvi.models.dto.WalletCreationDtoReq;
import br.com.pucc.projetointegradorvi.repositories.UserRepository2;
import br.com.pucc.projetointegradorvi.repositories.WalletRepository;

@Service
public class WalletServiceImp implements WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private UserRepository2 userRepository2;

	@Override
	public Optional<WalletModel> getWalletByUserId(String userId) {
		return walletRepository.findByUserId(Long.valueOf(userId));
	}

	@Override
	public List<WalletModel> getAllWallets() {
		return walletRepository.findAll();
	}

	@Override
	public Optional<WalletModel> getWalletById(String walletId) {
		return walletRepository.findById(Long.valueOf(walletId));
	}

	@Override
	public WalletModel createWallet(WalletCreationDtoReq wallet) {
		// Buscar a role "USER" no banco de dados
		Optional<UserModel2> um = userRepository2.findById(Long.valueOf(wallet.getUser()));

		if (um.isEmpty()) {
			throw new RuntimeException("Usuário não encontrada no banco de dados");
		}

		WalletModel wm = new WalletModel(wallet.getName(), wallet.getObjective(), wallet.getIntenFixIncPercent(),
				wallet.getIntenStockPercent(), wallet.getIntenFilPercent(), um.get());

		return walletRepository.saveAndFlush(wm);
	}

}
