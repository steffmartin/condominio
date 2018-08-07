package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Orcamento;

public interface OrcamentoDao extends CrudRepository<Orcamento, Long> {

}
