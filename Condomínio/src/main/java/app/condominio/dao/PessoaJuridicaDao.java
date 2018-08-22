package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Condominio;
import app.condominio.domain.PessoaJuridica;

public interface PessoaJuridicaDao extends PagingAndSortingRepository<PessoaJuridica, Long> {

	Boolean existsByCnpjAndCondominio(String cnpj, Condominio condominio);

	Boolean existsByCnpjAndCondominioAndIdPessoaNot(String cnpj, Condominio condominio, Long idPessoa);

}
