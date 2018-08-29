package app.condominio.service;

import java.math.BigDecimal;

import app.condominio.domain.Cobranca;

public interface CobrancaService extends CrudService<Cobranca, Long> {

	public BigDecimal inadimplencia();

}
