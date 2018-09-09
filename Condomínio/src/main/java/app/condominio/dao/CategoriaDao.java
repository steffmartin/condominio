package app.condominio.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Categoria;
import app.condominio.domain.Condominio;
import app.condominio.domain.enums.TipoCategoria;

public interface CategoriaDao extends PagingAndSortingRepository<Categoria, Long> {

	Boolean existsByOrdemAndCondominio(String ordem, Condominio condominio);

	Boolean existsByOrdemAndCondominioAndIdCategoriaNot(String ordem, Condominio condominio, Long idCategoria);

	List<Categoria> findAllByCondominioAndTipo(Condominio condominio, TipoCategoria tipo);

}
