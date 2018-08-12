package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Categoria;

public interface CategoriaDao extends CrudRepository<Categoria, Long> {

}
