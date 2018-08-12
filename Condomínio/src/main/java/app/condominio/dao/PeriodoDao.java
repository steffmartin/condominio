package app.condominio.dao;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Periodo;

public interface PeriodoDao extends CrudRepository<Periodo, Long> {

	boolean existsByInicioLessThanEqualAndFimGreaterThanEqual(LocalDate inicio, LocalDate fim);

	boolean existsByInicioAfterAndFimBefore(LocalDate inicio, LocalDate fim);

	boolean existsByInicioAfterAndFimBeforeAndIdPeriodoNot(LocalDate inicio, LocalDate fim, Long idPeriodo);

	Periodo findOneByInicioLessThanEqualAndFimGreaterThanEqual(LocalDate inicio, LocalDate fim);

}
