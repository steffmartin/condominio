package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Moradia;

public interface MoradiaDao extends CrudRepository<Moradia, Long> {

}
