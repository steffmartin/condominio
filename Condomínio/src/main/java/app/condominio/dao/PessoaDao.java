package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Pessoa;

public interface PessoaDao extends PagingAndSortingRepository<Pessoa, Long> {

}
