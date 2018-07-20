package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Pessoa;

public interface PessoaDao extends CrudRepository<Pessoa,Long> {

}
