package app.condominio.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import app.condominio.domain.Conta;
import app.condominio.domain.Lancamento;

public interface LancamentoDao extends PagingAndSortingRepository<Lancamento, Long> {

	@Query("select sum(valor) from #{#entityName} c where c.conta in :contas and c.data between :dataInicial and :dataFinal and c.reducao = :reducao")
	BigDecimal sumValorByContaInAndDataBetweenAndReducao(@Param("contas") Collection<Conta> contas,
			@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal,
			@Param("reducao") Boolean reducao);

	List<Lancamento> findAllByContaInAndDataBetweenOrderByDataAsc(Collection<Conta> conta, LocalDate inicio,
			LocalDate fim);

}
