package app.condominio.dao;

import java.time.LocalDate;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Cobranca;
import app.condominio.domain.Condominio;
import app.condominio.domain.Moradia;

public interface CobrancaDao extends PagingAndSortingRepository<Cobranca, Long> {

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndMoradiaAndCondominio(String numero, String parcela,
			LocalDate dataEmissao, Moradia moradia, Condominio condominoi);

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndMoradiaAndCondominioAndIdCobrancaNot(String numero, String parcela,
			LocalDate dataEmissao, Moradia moradia, Condominio condominoi, Long idCobranca);
}
