package app.condominio.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import app.condominio.domain.Cobranca;
import app.condominio.domain.Condominio;
import app.condominio.domain.Moradia;

public interface CobrancaDao extends PagingAndSortingRepository<Cobranca, Long> {

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndMoradiaAndCondominio(String numero, String parcela,
			LocalDate dataEmissao, Moradia moradia, Condominio condominoi);

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndMoradiaAndCondominioAndIdCobrancaNot(String numero, String parcela,
			LocalDate dataEmissao, Moradia moradia, Condominio condominoi, Long idCobranca);

	@Query("select sum(total) from #{#entityName} c where c.condominio = :condominio and c.dataRecebimento is null and c.dataVencimento < :data")
	BigDecimal sumTotalByCondominioAndDataVencimentoBeforeAndDataRecebimentoIsNull(
			@Param("condominio") Condominio condominio, @Param("data") LocalDate data);

	List<Cobranca> findAllByCondominioAndDataVencimentoBeforeAndDataRecebimentoIsNullOrderByMoradia_Bloco_SiglaAscMoradia_SiglaAsc(
			Condominio condominio, LocalDate data);
}
