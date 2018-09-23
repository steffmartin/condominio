package app.condominio.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Bloco;
import app.condominio.domain.Moradia;

public interface MoradiaDao extends PagingAndSortingRepository<Moradia, Long> {

	List<Moradia> findAllByBlocoInOrderByBlocoAscSiglaAsc(Collection<Bloco> bloco);

	Page<Moradia> findAllByBlocoInOrderByBlocoAscSiglaAsc(Collection<Bloco> bloco, Pageable pagina);

	Boolean existsBySiglaAndBloco(String sigla, Bloco bloco);

	Boolean existsBySiglaAndBlocoAndIdMoradiaNot(String sigla, Bloco bloco, Long idMoradia);

}
