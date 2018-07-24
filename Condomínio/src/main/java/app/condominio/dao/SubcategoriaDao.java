package app.condominio.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Categoria;
import app.condominio.domain.Subcategoria;

public interface SubcategoriaDao extends CrudRepository<Subcategoria,Long> {
	
	List<Subcategoria> findAllByCategoria(Iterable<Categoria> categoria);

}
