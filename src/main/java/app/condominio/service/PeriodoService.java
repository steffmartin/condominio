package app.condominio.service;

import java.time.LocalDate;

import app.condominio.domain.Periodo;

public interface PeriodoService extends CrudService<Periodo, Long> {

	public boolean haPeriodo(LocalDate data);

	public Periodo ler(LocalDate data);

}
