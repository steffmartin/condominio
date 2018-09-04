package app.condominio.service;

import java.math.BigDecimal;
import java.util.List;

import app.condominio.domain.Movimento;

public interface RelatorioService {

	public BigDecimal[] saldosAposMovimentos(List<Movimento> movimentos, BigDecimal saldoInicial);

}
