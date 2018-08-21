package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Categoria;
import app.condominio.domain.Condominio;

public interface CategoriaDao extends PagingAndSortingRepository<Categoria, Long> {

	Boolean existsByOrdemAndCondominio(String ordem, Condominio condominio);

	Boolean existsByOrdemAndCondominioAndIdCategoriaNot(String ordem, Condominio condominio, Long idCategoria);

}
