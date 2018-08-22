package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Condominio;
import app.condominio.domain.PessoaFisica;

public interface PessoaFisicaDao extends PagingAndSortingRepository<PessoaFisica, Long> {

	Boolean existsByCpfAndCondominio(String cpf, Condominio condominio);

	Boolean existsByCpfAndCondominioAndIdPessoaNot(String cpf, Condominio condominio, Long idPessoa);

}
