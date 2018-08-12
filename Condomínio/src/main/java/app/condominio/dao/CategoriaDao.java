package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Categoria;

public interface CategoriaDao extends PagingAndSortingRepository<Categoria, Long> {

}
