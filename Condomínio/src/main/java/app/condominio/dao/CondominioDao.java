package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Condominio;

public interface CondominioDao extends CrudRepository<Condominio,Long> {

}
