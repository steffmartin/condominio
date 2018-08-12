package app.condominio.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Conta;
import app.condominio.domain.Movimento;

public interface MovimentoDao extends CrudRepository<Movimento, Long> {

	List<Movimento> findAllByContaIn(Iterable<Conta> conta);

}
