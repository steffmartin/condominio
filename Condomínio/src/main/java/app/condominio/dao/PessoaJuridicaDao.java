package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.PessoaJuridica;

public interface PessoaJuridicaDao extends PagingAndSortingRepository<PessoaJuridica, Long> {

}
