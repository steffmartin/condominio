package app.condominio.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Bloco;
import app.condominio.domain.Condominio;

public interface BlocoDao extends PagingAndSortingRepository<Bloco, Long> {

	Boolean existsBySiglaAndCondominio(String sigla, Condominio condominio);

	Boolean existsBySiglaAndCondominioAndIdBlocoNot(String sigla, Condominio condominio, Long idBloco);

	Page<Bloco> findAllByCondominioOrderBySiglaAsc(Condominio condominio, Pageable pagina);

}
