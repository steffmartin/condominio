package app.condominio.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;
import app.condominio.domain.Subcategoria;

public interface OrcamentoDao extends PagingAndSortingRepository<Orcamento, Long> {

	List<Orcamento> findAllByPeriodoInOrderByPeriodo_InicioAscSubcategoria_CategoriaPai_OrdemAscSubcategoria_DescricaoAsc(
			Collection<Periodo> periodo);

	boolean existsByPeriodoAndSubcategoria(Periodo periodo, Subcategoria subcategoria);

	boolean existsByPeriodoAndSubcategoriaAndIdOrcamentoNot(Periodo periodo, Subcategoria subcategoria,
			Long idOrcamento);

}
