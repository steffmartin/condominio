package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Movimento;

public interface MovimentoDao extends CrudRepository<Movimento, Long> {

}
