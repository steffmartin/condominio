package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Cobranca;

public interface CobrancaDao extends CrudRepository<Cobranca,Long> {

}
