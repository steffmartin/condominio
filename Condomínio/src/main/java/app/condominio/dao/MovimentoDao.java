package app.condominio.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Conta;
import app.condominio.domain.Movimento;

public interface MovimentoDao extends PagingAndSortingRepository<Movimento, Long> {

	// TODO definir ordenação
	List<Movimento> findAllByContaIn(Collection<Conta> conta);

}
