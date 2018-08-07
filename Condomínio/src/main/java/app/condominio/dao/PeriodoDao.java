package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Periodo;

public interface PeriodoDao extends CrudRepository<Periodo, Long> {

}
