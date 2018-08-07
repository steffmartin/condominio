package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Lancamento;

public interface LancamentoDao extends CrudRepository<Lancamento, Long> {

}
