package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import app.condominio.domain.Movimento;
import app.condominio.domain.Periodo;

public interface MovimentoService extends CrudService<Movimento, Long> {

	public BigDecimal[] receitaDespesaEntre(LocalDate inicio, LocalDate fim);

	public BigDecimal[] receitaDespesa(Periodo periodo);

}
