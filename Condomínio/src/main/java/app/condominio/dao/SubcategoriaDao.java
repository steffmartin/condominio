package app.condominio.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Categoria;
import app.condominio.domain.Subcategoria;

public interface SubcategoriaDao extends PagingAndSortingRepository<Subcategoria, Long> {

	List<Subcategoria> findAllByCategoriaPaiInOrderByCategoriaPai_OrdemAscDescricao(Collection<Categoria> categoriaPai);

	int countByCategoriaPaiIn(Collection<Categoria> categoriaPai);

}
