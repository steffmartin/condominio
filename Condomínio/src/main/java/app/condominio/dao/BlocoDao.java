package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Bloco;

public interface BlocoDao extends PagingAndSortingRepository<Bloco, Long> {

}
