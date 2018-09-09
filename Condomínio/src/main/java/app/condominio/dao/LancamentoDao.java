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
import app.condominio.domain.Subcategoria;

public interface LancamentoDao extends PagingAndSortingRepository<Lancamento, Long> {

	@Query("select sum(valor) from #{#entityName} l where l.conta in :contas and l.data between :dataInicial and :dataFinal and l.reducao = :reducao")
	BigDecimal sumValorByContaInAndDataBetweenAndReducao(@Param("contas") Collection<Conta> contas,
			@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal,
			@Param("reducao") Boolean reducao);

	@Query("select sum(valor) from #{#entityName} l where l.conta in :contas and l.data >= :data and l.reducao = :reducao")
	BigDecimal sumValorByContaInAndDataGreaterThanEqualAndReducao(@Param("contas") Collection<Conta> contas,
			@Param("data") LocalDate data, @Param("reducao") Boolean reducao);

	@Query("select sum(valor) from #{#entityName} l where l.conta in :contas and l.data between :dataInicial and :dataFinal and l.subcategoria = :subcategoria ")
	BigDecimal sumValorByContaInAndDataBetweenAndSubcategoria(@Param("contas") Collection<Conta> contas,
			@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal,
			@Param("subcategoria") Subcategoria subcategoria);

	List<Lancamento> findAllByContaInAndDataBetweenOrderByDataAsc(Collection<Conta> conta, LocalDate inicio,
			LocalDate fim);

}
