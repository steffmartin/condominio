package app.condominio.dao;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import app.condominio.domain.Condominio;
import app.condominio.domain.Conta;

public interface ContaDao extends PagingAndSortingRepository<Conta, Long> {

	Page<Conta> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);

	Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);

	Boolean existsBySiglaAndCondominioAndIdContaNot(String sigla, Condominio condominio, Long idConta);

	@Query("select sum(saldoAtual) from #{#entityName} c where c.condominio = :condominio")
	BigDecimal sumSaldoAtualByCondominio(@Param("condominio") Condominio condominio);

}
