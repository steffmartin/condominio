package app.condominio.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Condominio;
import app.condominio.domain.Pessoa;

public interface PessoaDao extends PagingAndSortingRepository<Pessoa, Long> {

	// LATER ordenação do sobrenome?
	Page<Pessoa> findAllByCondominioOrderByNome(Condominio condominio, Pageable pagina);

}
