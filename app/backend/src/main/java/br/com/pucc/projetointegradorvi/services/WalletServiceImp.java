package br.com.pucc.projetointegradorvi.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.pucc.projetointegradorvi.models.FixedTransactionModel;
import br.com.pucc.projetointegradorvi.models.TickerModel;
import br.com.pucc.projetointegradorvi.models.UserModel;
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
import br.com.pucc.projetointegradorvi.repositories.FixedTransactionRepository;
import br.com.pucc.projetointegradorvi.repositories.TickerRepository;
import br.com.pucc.projetointegradorvi.repositories.VariableTransactionRepository;
import br.com.pucc.projetointegradorvi.repositories.WalletQueriedRepository;
import br.com.pucc.projetointegradorvi.repositories.WalletRepository;

@Service
public class WalletServiceImp implements WalletService {

	@Autowired
	private AuthService authService;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private WalletQueriedRepository walletQueriedRepository;

	@Autowired
	private FixedTransactionRepository fixedTransactionRepository;

	@Autowired
	private VariableTransactionRepository variableTransactionRepository;

	@Autowired
	private TickerRepository tickerRepository;

	// WALLET
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

	// FIXED TRANSACTION
	@Override
	public FixedTransactionModel createWalletFixedTransaction(String walletId, FixedTransactionReqDto ftransaction) {
		Optional<WalletModel> optionalWallet = walletRepository.findById(Long.valueOf(walletId));

		if (optionalWallet.isPresent()) {
			WalletModel w = optionalWallet.get();
			FixedTransactionModel ftm = new FixedTransactionModel(w, ftransaction.getInstitution(),
					ftransaction.getType(), ftransaction.getValue(), ftransaction.getStartDate(),
					ftransaction.getEndDate(), ftransaction.getIndexName(), ftransaction.getValue());
			return this.fixedTransactionRepository.saveAndFlush(ftm);
		}

		throw new RuntimeException("Wallet not found with id: " + walletId);
	}

	@Override
	public List<FixedTransactionDto> getWalletFixedTransaction(String walletId, Optional<String> endDate) {
		LocalDate ld = endDate.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());

		List<FixedTransactionDto> investments = this.walletQueriedRepository
				.findWalletFixedTransaction(Long.valueOf(walletId), ld);

		if (investments == null) {
			return Collections.emptyList();
		}

		return investments;
	}

	@Override
	public List<FixedTransactionByInstitutionDto> getWalletFixedTransactionByInstitution(String walletId,
			Optional<String> endDate) {
		LocalDate ld = endDate.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());

		List<FixedTransactionByInstitutionDto> investments = this.walletQueriedRepository
				.findWalletFixedTransactionsByInstitution(Long.valueOf(walletId), ld);

		if (investments == null) {
			return Collections.emptyList();
		}

		return investments;
	}

	@Override
	public FixedTransactionWithVariationDto getWalletFixedTransactionWithVariation(String walletId,
			Optional<String> endDate) {
		LocalDate ld = endDate.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());

		FixedTransactionWithVariationDto investment = this.walletQueriedRepository
				.findWalletFixedTransactionWithVariation(Long.valueOf(walletId), ld);

		return investment;

	}

	@Override
	public List<FixedTransactionWithVariationByInstitutionDto> getWalletFixedTransactionWithVariationByInstitution(
			String walletId, Optional<String> endDate) {
		LocalDate ld = endDate.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());

		List<FixedTransactionWithVariationByInstitutionDto> investments = this.walletQueriedRepository
				.findWalletFixedTransactionWithVariationByInstitution(Long.valueOf(walletId), ld);

		if (investments == null) {
			return Collections.emptyList();
		}

		return investments;
	}

	@Override
	public Optional<FixedTransactionModel> deleteWalletFixedTransaction(String walletId, String ftId) {
		Optional<WalletModel> w = walletRepository.findById(Long.valueOf(walletId));
		if (w.isPresent()) {
			Optional<FixedTransactionModel> f = this.fixedTransactionRepository.findById(Long.valueOf(ftId));
			if (f.isPresent()) {
				this.fixedTransactionRepository.delete(f.get());
				return f;
			}
			throw new RuntimeException("Fixed transaction not found with id: " + ftId);
		}
		throw new RuntimeException("Wallet not found with id: " + walletId);
	}

	// VARIABLE TRANSACTION
	@Override
	public VariableTransactionModel createWalletVariableTransaction(String walletId,
			VariableTransactionReqDto vtransaction) {
		Optional<WalletModel> w = walletRepository.findById(Long.valueOf(walletId));

		if (w.isPresent()) {

			Optional<TickerModel> t = this.tickerRepository.findById(vtransaction.getTicker());

			if (t.isPresent()) {

				VariableTransactionModel v = new VariableTransactionModel(w.get(), t.get(), vtransaction.getBuyOrSale(),
						vtransaction.getDate(), vtransaction.getAmount(), vtransaction.getPrice());
				return this.variableTransactionRepository.saveAndFlush(v);
			}
			throw new RuntimeException("Ticker not found with id: " + vtransaction.getTicker());
		}

		throw new RuntimeException("Wallet not found with id: " + walletId);
	}

	@Override
	public List<VariableTransactionDto> getWalletVariableTransaction(String walletId, Optional<String> startAt,
			Optional<String> endAt) {
		LocalDate sdt = startAt.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());
		LocalDate edt = endAt.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());

		List<VariableTransactionDto> investments = this.walletQueriedRepository
				.findWalletVariableTransaction(Long.valueOf(walletId), sdt, edt);

		if (investments == null) {
			return Collections.emptyList();
		}

		return investments;
	}

	@Override
	public Optional<VariableTransactionModel> deleteWalletVariableTransaction(String walletId, String vtId) {
		Optional<WalletModel> w = walletRepository.findById(Long.valueOf(walletId));
		if (w.isPresent()) {
			Optional<VariableTransactionModel> v = this.variableTransactionRepository.findById(Long.valueOf(vtId));
			if (v.isPresent()) {
				this.variableTransactionRepository.delete(v.get());
				return v;
			}
			throw new RuntimeException("Fixed transaction not found with id: " + vtId);
		}
		throw new RuntimeException("Wallet not found with id: " + walletId);
	}

	@Override
	public VariableTransactionResumeDto getWalletVariableTransactionResume(String walletId, Optional<String> startAt,
			Optional<String> endAt) {
		LocalDate sdt = startAt.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());
		LocalDate edt = endAt.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());

		VariableTransactionResumeDto investment = this.walletQueriedRepository
				.findWalletVariableTransactionResume(Long.valueOf(walletId), sdt, edt);

		return investment;
	}

	@Override
	public List<VariableTransactionDto> getWalletVariableTransactionWithVariation(String walletId,
			Optional<String> endAt) {
		LocalDate edt = endAt.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());

		List<VariableTransactionDto> investments = this.walletQueriedRepository
				.findWalletVariableTransactionWithVariation(Long.valueOf(walletId), edt);

		if (investments == null) {
			return Collections.emptyList();
		}

		return investments;
	}

	@Override
	public VariableTransactionResumeDto getWalletVariableTransactionResumeWithVariation(String walletId,
			Optional<String> endAt) {
		LocalDate edt = endAt.map(date -> LocalDate.parse(date)).orElse(LocalDate.now());

		VariableTransactionResumeDto investment = this.walletQueriedRepository
				.findWalletVariableTransactionResumeWithVariation(Long.valueOf(walletId), edt);

		return investment;
	}

}
