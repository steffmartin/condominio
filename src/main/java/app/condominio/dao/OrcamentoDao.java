package app.condominio.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;
import app.condominio.domain.Subcategoria;
import app.condominio.domain.enums.TipoCategoria;

public interface OrcamentoDao extends PagingAndSortingRepository<Orcamento, Long> {

	List<Orcamento> findAllByPeriodoInOrderByPeriodoDescSubcategoriaAsc(Collection<Periodo> periodo);

	Page<Orcamento> findAllByPeriodoInOrderByPeriodoDescSubcategoriaAsc(Collection<Periodo> periodo, Pageable pagina);

	boolean existsByPeriodoAndSubcategoria(Periodo periodo, Subcategoria subcategoria);

	boolean existsByPeriodoAndSubcategoriaAndIdOrcamentoNot(Periodo periodo, Subcategoria subcategoria,
			Long idOrcamento);

	@Query("select sum(orcado) from #{#entityName} o where o.periodo = :periodo and o.subcategoria.categoriaPai.tipo = :tipo")
	BigDecimal sumByPeriodoAndSubcategoria_CategoriaPai_Tipo(@Param("periodo") Periodo periodo,
			@Param("tipo") TipoCategoria tipo);

	@Query("select sum(orcado) from #{#entityName} o where o.periodo = :periodo and o.subcategoria.categoriaPai.ordem like :ordem%")
	BigDecimal sumByPeriodoAndSubcategoria_CategoriaPai_OrdemStartingWith(@Param("periodo") Periodo periodo,
			@Param("ordem") String ordem);

	Orcamento findOneByPeriodoAndSubcategoria(Periodo periodo, Subcategoria subcategoria);

}
