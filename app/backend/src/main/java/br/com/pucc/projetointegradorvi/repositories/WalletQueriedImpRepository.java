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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
@Transactional
public class WalletQueriedImpRepository implements WalletQueriedRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<FixedTransactionDto> findFixedTransaction(Long walletId, LocalDate currentDate) {
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
	public List<FixedTransactionByInstitutionDto> findFixedTransactionsByInstitution(Long walletId,
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
	public FixedTransactionWithVariationDto findFixedTransactionWithVariation(Long walletId, LocalDate currentDate) {
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
	public List<FixedTransactionWithVariationByInstitutionDto> findFixedTransactionWithVariationByInstitution(
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
}
