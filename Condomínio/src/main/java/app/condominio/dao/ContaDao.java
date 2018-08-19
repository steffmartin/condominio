package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Condominio;
import app.condominio.domain.Conta;

public interface ContaDao extends PagingAndSortingRepository<Conta, Long> {

	Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);

	Boolean existsBySiglaAndCondominioAndIdContaNot(String sigla, Condominio condominio, Long idConta);

}
