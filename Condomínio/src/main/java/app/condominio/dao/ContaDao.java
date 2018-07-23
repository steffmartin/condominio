package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Conta;

public interface ContaDao extends CrudRepository<Conta, Long> {

}
