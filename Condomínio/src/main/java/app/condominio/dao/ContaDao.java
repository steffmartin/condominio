package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Conta;

public interface ContaDao extends PagingAndSortingRepository<Conta, Long> {

}
