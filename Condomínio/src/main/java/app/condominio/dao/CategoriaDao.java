package app.condominio.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Categoria;
import app.condominio.domain.Condominio;

public interface CategoriaDao extends CrudRepository<Categoria,Long> {

}
