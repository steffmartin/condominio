package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import app.condominio.domain.Movimento;

public interface RelatorioService {

	public BigDecimal saldoAtualTodasContas();

	public BigDecimal saldoInicialTodasContasEm(LocalDate data);

	public BigDecimal saldoFinalTodasContasEm(LocalDate data);

	public BigDecimal inadimplenciaAtual();

	public BigDecimal[] receitaDespesaMesAtual();

	public BigDecimal[] receitaDespesaRealizadaPeriodoAtual();

	public BigDecimal[] receitaDespesaOrcadaPeriodoAtual();

	public List<Movimento> lancamentosEntre(LocalDate inicio, LocalDate fim);

	public BigDecimal[] saldosAposMovimentos(List<Movimento> movimentos, BigDecimal saldoInicial);

}
