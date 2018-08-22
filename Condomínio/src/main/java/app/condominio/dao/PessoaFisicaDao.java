package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.PessoaFisica;

public interface PessoaFisicaDao extends PagingAndSortingRepository<PessoaFisica, Long> {

}
