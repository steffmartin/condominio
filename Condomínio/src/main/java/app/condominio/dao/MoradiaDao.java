package app.condominio.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Bloco;
import app.condominio.domain.Moradia;

public interface MoradiaDao extends CrudRepository<Moradia, Long> {

	List<Moradia> findAllByBlocoIn(Iterable<Bloco> bloco);

}
