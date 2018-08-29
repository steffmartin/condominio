package app.condominio.service;

import java.math.BigDecimal;

import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;

public interface OrcamentoService extends CrudService<Orcamento, Long> {

	public BigDecimal[] totalOrcado(Periodo periodo);

}
