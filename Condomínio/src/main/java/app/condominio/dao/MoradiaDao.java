package app.condominio.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Bloco;
import app.condominio.domain.Moradia;

public interface MoradiaDao extends PagingAndSortingRepository<Moradia, Long> {

	List<Moradia> findAllByBlocoInOrderByBloco_SiglaAscSiglaAsc(Collection<Bloco> bloco);

	Boolean existsBySiglaAndBloco(String sigla, Bloco bloco);

	Boolean existsBySiglaAndBlocoAndIdMoradiaNot(String sigla, Bloco bloco, Long idMoradia);

	void deleteMoradia_RelacoesByIdMoradia(Long idMoradia);

}
