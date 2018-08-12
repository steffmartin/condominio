package app.condominio.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;
import app.condominio.domain.Subcategoria;

public interface OrcamentoDao extends CrudRepository<Orcamento, Long> {

	List<Orcamento> findAllByPeriodoIn(Iterable<Periodo> periodo);

	boolean existsByPeriodoAndSubcategoria(Periodo periodo, Subcategoria subcategoria);

	boolean existsByPeriodoAndSubcategoriaAndIdOrcamentoNot(Periodo periodo, Subcategoria subcategoria,
			Long idOrcamento);

}
