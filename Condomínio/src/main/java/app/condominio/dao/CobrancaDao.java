package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Cobranca;

public interface CobrancaDao extends PagingAndSortingRepository<Cobranca, Long> {

}
