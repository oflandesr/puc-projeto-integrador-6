package br.com.pucc.projetointegradorvi.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationByInstitutionDto;
import br.com.pucc.projetointegradorvi.models.dto.FixedTransactionWithVariationDto;
import br.com.pucc.projetointegradorvi.models.dto.VariableTransactionDto;
import br.com.pucc.projetointegradorvi.models.dto.VariableTransactionResumeDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
@Transactional
public class WalletQueriedImpRepository implements WalletQueriedRepository {
	@PersistenceContext
	private EntityManager entityManager;

	// FIXED TRANSACTION
	@Override
	public List<FixedTransactionDto> findWalletFixedTransaction(Long walletId, LocalDate currentDate) {
		String nativeQuery = """
				    WITH CurrentIndexes AS (
				        SELECT
				            (SELECT SELIC FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS selic_rate,
				            (SELECT CDI FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS cdi_rate,
				            (SELECT IPCA FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS ipca_rate
				    )
				    SELECT
				        t.ID,
				        t.INSTITUTION,
				        t.TYPE,
				        t.VALUE,
				        t.START_DATE,
				        t.END_DATE,
				        t.INDEX_NAME,
				        t.TAX_VALUE,
				        DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) AS TIME_APPLIED_IN_DAYS,
				        ROUND(
				            CASE
				                WHEN t.TYPE = 'PRE' THEN
				                    t.VALUE * POWER(1 + t.TAX_VALUE / 100, DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
				                WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'SELIC' THEN
				                    t.VALUE * POWER(1 + ((CurrentIndexes.selic_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
				                WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'CDI' THEN
				                    t.VALUE * POWER(1 + ((CurrentIndexes.cdi_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
				                WHEN t.TYPE = 'HIBRIDO' AND t.INDEX_NAME = 'IPCA' THEN
				                    t.VALUE * POWER(1 + ((CurrentIndexes.ipca_rate + t.TAX_VALUE) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
				                ELSE
				                    t.VALUE
				            END, 2
				        ) AS CURRENT_VALUE
				    FROM
				        TRANSACTIONS_FIXED_INCOME t, CurrentIndexes
				    WHERE
				        t.WALLET_ID = :walletId;
				""";

		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter("walletId", walletId);
		query.setParameter("currentDate", currentDate);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		return results.stream()
				.map(result -> new FixedTransactionDto(((Number) result[0]).longValue(), (String) result[1],
						(String) result[2], ((Number) result[3]).doubleValue(), ((Date) result[4]).toLocalDate(),
						((Date) result[5]).toLocalDate(), (String) result[6], ((Number) result[7]).doubleValue(),
						((Number) result[8]).intValue(), ((Number) result[9]).doubleValue()))
				.toList();
	}

	@Override
	public List<FixedTransactionByInstitutionDto> findWalletFixedTransactionsByInstitution(Long walletId,
			LocalDate currentDate) {
		String nativeQuery = """
				    WITH CurrentIndexes AS (
				        SELECT
				            (SELECT SELIC FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS selic_rate,
				            (SELECT CDI FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS cdi_rate,
				            (SELECT IPCA FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS ipca_rate
				    )
				    SELECT
				        t.INSTITUTION,
				        SUM(ROUND(
				            CASE
				                WHEN t.TYPE = 'PRE' THEN
				                    t.VALUE * POWER(1 + t.TAX_VALUE / 100, DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
				                WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'SELIC' THEN
				                    t.VALUE * POWER(1 + ((CurrentIndexes.selic_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
				                WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'CDI' THEN
				                    t.VALUE * POWER(1 + ((CurrentIndexes.cdi_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
				                WHEN t.TYPE = 'HIBRIDO' AND t.INDEX_NAME = 'IPCA' THEN
				                    t.VALUE * POWER(1 + ((CurrentIndexes.ipca_rate + t.TAX_VALUE) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
				                ELSE
				                    t.VALUE
				            END, 2
				        )) AS TOTAL
				    FROM
				        TRANSACTIONS_FIXED_INCOME t, CurrentIndexes
				    WHERE
				        t.WALLET_ID = :walletId
				    GROUP BY t.INSTITUTION;
				""";

		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter("walletId", walletId);
		query.setParameter("currentDate", currentDate);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		return results.stream().map(
				result -> new FixedTransactionByInstitutionDto((String) result[0], ((Number) result[1]).doubleValue()))
				.toList();
	}

	@Override
	public FixedTransactionWithVariationDto findWalletFixedTransactionWithVariation(Long walletId,
			LocalDate currentDate) {
		String nativeQuery = """
				    WITH CurrentIndexes AS (
				        SELECT
				            (SELECT SELIC FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS selic_rate,
				            (SELECT CDI FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS cdi_rate,
				            (SELECT IPCA FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS ipca_rate
				    )
				    SELECT
					    SUM(ROUND(
					        CASE
					            WHEN t.TYPE = 'PRE' THEN
					                t.VALUE * POWER(1 + t.TAX_VALUE / 100, DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					            WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'SELIC' THEN
					                t.VALUE * POWER(1 + ((@selic_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					            WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'CDI' THEN
					                t.VALUE * POWER(1 + ((@cdi_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					            WHEN t.TYPE = 'HIBRIDO' AND t.INDEX_NAME = 'IPCA' THEN
					                t.VALUE * POWER(1 + ((@ipca_rate + t.TAX_VALUE) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					            ELSE
					                t.VALUE
					        END, 2)) AS TOTAL
					FROM
					    TRANSACTIONS_FIXED_INCOME t, CurrentIndexes
					WHERE
					    t.WALLET_ID = :walletId;
				""";

		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter("walletId", walletId);
		query.setParameter("currentDate", currentDate);

		Number result = (Number) query.getSingleResult();

		return new FixedTransactionWithVariationDto(result != null ? result.doubleValue() : 0.0);
	}

	@Override
	public List<FixedTransactionWithVariationByInstitutionDto> findWalletFixedTransactionWithVariationByInstitution(
			Long walletId, LocalDate currentDate) {
		String nativeQuery = """
				    WITH CurrentIndexes AS (
				        SELECT
				            (SELECT SELIC FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS selic_rate,
				            (SELECT CDI FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS cdi_rate,
				            (SELECT IPCA FROM INDEXES ORDER BY DATE DESC LIMIT 1) AS ipca_rate
				    )
				    SELECT
					    t.INSTITUTION,
					    SUM(t.VALUE) AS VALUE,
					    SUM(
					        ROUND(
					            CASE
					                WHEN t.TYPE = 'PRE' THEN
					                    t.VALUE * POWER(1 + t.TAX_VALUE / 100, DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'SELIC' THEN
					                    t.VALUE * POWER(1 + ((@selic_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'CDI' THEN
					                    t.VALUE * POWER(1 + ((@cdi_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                WHEN t.TYPE = 'HIBRIDO' AND t.INDEX_NAME = 'IPCA' THEN
					                    t.VALUE * POWER(1 + ((@ipca_rate + t.TAX_VALUE) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                ELSE
					                    t.VALUE
					            END, 2)
					    ) AS CURRENT_VALUE,
					    SUM(
					        ROUND(
					            CASE
					                WHEN t.TYPE = 'PRE' THEN
					                    t.VALUE * POWER(1 + t.TAX_VALUE / 100, DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'SELIC' THEN
					                    t.VALUE * POWER(1 + ((@selic_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'CDI' THEN
					                    t.VALUE * POWER(1 + ((@cdi_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                WHEN t.TYPE = 'HIBRIDO' AND t.INDEX_NAME = 'IPCA' THEN
					                    t.VALUE * POWER(1 + ((@ipca_rate + t.TAX_VALUE) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                ELSE
					                    t.VALUE
					            END, 2)
					    ) - SUM(t.VALUE) AS GAIN,
					    ROUND(
					        (
					            SUM(
					                ROUND(
					                    CASE
					                        WHEN t.TYPE = 'PRE' THEN
					                            t.VALUE * POWER(1 + t.TAX_VALUE / 100, DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                        WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'SELIC' THEN
					                            t.VALUE * POWER(1 + ((@selic_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                        WHEN t.TYPE = 'POS' AND t.INDEX_NAME = 'CDI' THEN
					                            t.VALUE * POWER(1 + ((@cdi_rate * t.TAX_VALUE / 100) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                        WHEN t.TYPE = 'HIBRIDO' AND t.INDEX_NAME = 'IPCA' THEN
					                            t.VALUE * POWER(1 + ((@ipca_rate + t.TAX_VALUE) / 100), DATEDIFF(LEAST(t.END_DATE, :currentDate), t.START_DATE) / 365)
					                        ELSE
					                            t.VALUE
					                    END, 2)
					            ) / SUM(t.VALUE) - 1
					        ) * 100, 2
					    ) AS PERCENTAGE_GAIN
					FROM
					    TRANSACTIONS_FIXED_INCOME t, CurrentIndexes
					WHERE
					    t.WALLET_ID = :walletId
					GROUP BY
					    t.INSTITUTION;
				""";

		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter("walletId", walletId);
		query.setParameter("currentDate", currentDate);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		return results.stream()
				.map(result -> new FixedTransactionWithVariationByInstitutionDto((String) result[0],
						((Number) result[1]).doubleValue(), ((Number) result[2]).doubleValue(),
						((Number) result[3]).doubleValue(), ((Number) result[4]).doubleValue()))
				.toList();
	}

	// VARIABLE TRANSACTION
	@Override
	public List<VariableTransactionDto> findWalletVariableTransaction(Long walletId, LocalDate startDate,
			LocalDate endDate) {
		String nativeQuery = """
				    SELECT
					    TICKER_ID,
					    SUM(CASE WHEN BUY_OR_SALE = 1 THEN AMOUNT ELSE 0 END) AS TOTAL_AMOUNT_BUYED,
					    SUM(CASE WHEN BUY_OR_SALE = 0 THEN AMOUNT ELSE 0 END) AS TOTAL_AMOUNT_SOLD,
					    AVG(CASE WHEN BUY_OR_SALE = 1 THEN PRICE ELSE NULL END) AS AVG_VALUE_INVESTED
					FROM
					    TRANSACTIONS_VARIABLE_INCOME
					WHERE
					    WALLET_ID = :walletId
					    AND DATE >= :startDate AND DATE <= :endDate
					GROUP BY
					    TICKER_ID;
				""";

		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter("walletId", walletId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		return results.stream()
				.map(result -> new VariableTransactionDto((String) result[0], ((Number) result[1]).doubleValue(),
						((Number) result[2]).doubleValue(), ((Number) result[3]).doubleValue()))
				.toList();
	}

	@Override
	public VariableTransactionResumeDto findWalletVariableTransactionResume(Long walletId, LocalDate startDate,
			LocalDate endDate) {
		String nativeQuery = """
				    SELECT
				        SUM(CASE WHEN BUY_OR_SALE = 1 THEN AMOUNT ELSE 0 END) AS TOTAL_AMOUNT_BUYED,
				        SUM(CASE WHEN BUY_OR_SALE = 0 THEN AMOUNT ELSE 0 END) AS TOTAL_AMOUNT_SOLD,
				        AVG(CASE WHEN BUY_OR_SALE = 1 THEN PRICE ELSE NULL END) AS AVG_VALUE_INVESTED
				    FROM
				        TRANSACTIONS_VARIABLE_INCOME
				    WHERE
				        WALLET_ID = :walletId
				        AND DATE >= :startDate AND DATE <= :endDate
				""";

		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter("walletId", walletId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		Object[] result = (Object[]) query.getSingleResult();

		Double totalAmountBuyed = result[0] != null ? ((Number) result[0]).doubleValue() : 0.0;
		Double totalAmountSold = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
		Double avgValueInvested = result[2] != null ? ((Number) result[2]).doubleValue() : 0.0;

		return new VariableTransactionResumeDto(totalAmountBuyed, totalAmountSold, avgValueInvested);
	}

	@Override
	public List<VariableTransactionDto> findWalletVariableTransactionWithVariation(Long walletId, LocalDate endDate) {
		String nativeQuery = """
				    WITH net_shares AS (
				        SELECT
				            TICKER_ID,
				            SUM(CASE WHEN BUY_OR_SALE = 1 THEN AMOUNT ELSE 0 END) AS TOTAL_AMOUNT_BUYED,
				            SUM(CASE WHEN BUY_OR_SALE = 0 THEN AMOUNT ELSE 0 END) AS TOTAL_AMOUNT_SOLD,
				            AVG(CASE WHEN BUY_OR_SALE = 1 THEN PRICE ELSE NULL END) AS AVG_VALUE_INVESTED
				        FROM
				            TRANSACTIONS_VARIABLE_INCOME
				        WHERE
				            WALLET_ID = :walletId
				            AND DATE <= :endDate
				        GROUP BY
				            TICKER_ID
				    ),
				    last_prices AS (
				        SELECT
				            p1.TICKER AS TICKER_ID,
				            p1.CLOSE AS PRICE
				        FROM
				            PRICES p1
				        INNER JOIN (
				            SELECT
				                TICKER,
				                MAX(TIMESTAMP) AS MAX_TIMESTAMP
				            FROM
				                PRICES
				            WHERE
				                TIMESTAMP <= UNIX_TIMESTAMP(:endDate + INTERVAL 1 DAY) - 1
				            GROUP BY
				                TICKER
				        ) p2 ON p1.TICKER = p2.TICKER AND p1.TIMESTAMP = p2.MAX_TIMESTAMP
				    )
				    SELECT
				        ns.TICKER_ID,
				        COALESCE(SUM(ns.TOTAL_AMOUNT_BUYED), 0) AS TOTAL_BUYED,
				        COALESCE(SUM(ns.TOTAL_AMOUNT_SOLD), 0) AS TOTAL_SOLD,
				        COALESCE(SUM((ns.TOTAL_AMOUNT_BUYED - ns.TOTAL_AMOUNT_SOLD) * lp.PRICE), 0) AS TOTAL_VARIABLE_INVESTED_VALUE
				    FROM
				        net_shares ns
				    INNER JOIN
				        last_prices lp ON ns.TICKER_ID = lp.TICKER_ID
				    GROUP BY
				        ns.TICKER_ID;
				""";

		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter("walletId", walletId);
		query.setParameter("endDate", endDate);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		return results.stream()
				.map(result -> new VariableTransactionDto((String) result[0], ((Number) result[1]).doubleValue(),
						((Number) result[2]).doubleValue(), ((Number) result[3]).doubleValue()))
				.toList();
	}

	@Override
	public VariableTransactionResumeDto findWalletVariableTransactionResumeWithVariation(Long walletId,
			LocalDate endDate) {
		String nativeQuery = """
				    WITH net_shares AS (
				        SELECT
				            TICKER_ID,
				            SUM(CASE WHEN BUY_OR_SALE = 1 THEN AMOUNT ELSE 0 END) AS TOTAL_AMOUNT_BUYED,
				            SUM(CASE WHEN BUY_OR_SALE = 0 THEN AMOUNT ELSE 0 END) AS TOTAL_AMOUNT_SOLD,
				            AVG(CASE WHEN BUY_OR_SALE = 1 THEN PRICE ELSE NULL END) AS AVG_VALUE_INVESTED
				        FROM
				            TRANSACTIONS_VARIABLE_INCOME
				        WHERE
				            WALLET_ID = :walletId
				            AND DATE <= :endDate
				        GROUP BY
				            TICKER_ID
				    ),
				    last_prices AS (
				        SELECT
				            p1.TICKER AS TICKER_ID,
				            p1.CLOSE AS PRICE
				        FROM
				            PRICES p1
				        INNER JOIN (
				            SELECT
				                TICKER,
				                MAX(TIMESTAMP) AS MAX_TIMESTAMP
				            FROM
				                PRICES
				            WHERE
				                TIMESTAMP <= UNIX_TIMESTAMP(:endDate + INTERVAL 1 DAY) - 1
				            GROUP BY
				                TICKER
				        ) p2 ON p1.TICKER = p2.TICKER AND p1.TIMESTAMP = p2.MAX_TIMESTAMP
				    )
				    SELECT
				        SUM(ns.TOTAL_AMOUNT_BUYED) AS TOTAL_BUYED,
				        SUM(ns.TOTAL_AMOUNT_SOLD) AS TOTAL_SOLD,
				        SUM((ns.TOTAL_AMOUNT_BUYED - ns.TOTAL_AMOUNT_SOLD) * lp.PRICE) AS TOTAL_INVESTED_VALUE
				    FROM
				        net_shares ns
				    INNER JOIN
				        last_prices lp ON ns.TICKER_ID = lp.TICKER_ID;
				""";

		Query query = entityManager.createNativeQuery(nativeQuery);
		query.setParameter("walletId", walletId);
		query.setParameter("endDate", endDate);

		Object[] result = (Object[]) query.getSingleResult();

		// Processa os resultados, validando valores nulos
		Double totalAmountBuyed = result[0] != null ? ((Number) result[0]).doubleValue() : 0.0;
		Double totalAmountSold = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;
		Double totalInvestedValue = result[2] != null ? ((Number) result[2]).doubleValue() : 0.0;

		// Retorna o DTO ajustado
		return new VariableTransactionResumeDto(totalAmountBuyed, totalAmountSold, totalInvestedValue);
	}

}
