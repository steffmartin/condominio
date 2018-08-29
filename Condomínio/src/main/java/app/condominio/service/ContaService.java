package app.condominio.service;

import java.math.BigDecimal;

import app.condominio.domain.Conta;

public interface ContaService extends CrudService<Conta, Long> {

	public BigDecimal saldoAtual();

}
