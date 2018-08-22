package app.condominio.dao;

import java.time.LocalDate;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Condominio;
import app.condominio.domain.Periodo;

public interface PeriodoDao extends PagingAndSortingRepository<Periodo, Long> {

	boolean existsByCondominioAndInicioLessThanEqualAndFimGreaterThanEqual(Condominio condominio, LocalDate inicio,
			LocalDate fim);

	boolean existsByCondominioAndInicioLessThanEqualAndFimGreaterThanEqualAndIdPeriodoNot(Condominio condominio,
			LocalDate inicio, LocalDate fim, Long idPeriodo);

	boolean existsByCondominioAndInicioAfterAndFimBefore(Condominio condominio, LocalDate inicio, LocalDate fim);

	boolean existsByCondominioAndInicioAfterAndFimBeforeAndIdPeriodoNot(Condominio condominio, LocalDate inicio,
			LocalDate fim, Long idPeriodo);

	Periodo findOneByCondominioAndInicioLessThanEqualAndFimGreaterThanEqual(Condominio condominio, LocalDate inicio,
			LocalDate fim);

}
