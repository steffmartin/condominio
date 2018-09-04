package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.domain.Conta;
import app.condominio.domain.Movimento;
import app.condominio.domain.Periodo;
import app.condominio.domain.enums.TipoCategoria;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class RelatorioServiceImpl implements RelatorioService {

	@Autowired
	ContaService contaService;

	@Autowired
	MovimentoService movimentoService;

	@Autowired
	CobrancaService cobrancaService;

	@Autowired
	OrcamentoService orcamentoService;

	@Autowired
	PeriodoService periodoService;

	@Override
	public BigDecimal saldoAtualTodasContas() {
		return contaService.saldoAtual();
	}

	@Override
	public BigDecimal saldoInicialTodasContasEm(LocalDate data) {
		return saldoFinalTodasContasEm(data.minusDays(1));
	}

	@Override
	public BigDecimal saldoFinalTodasContasEm(LocalDate data) {
		return BigDecimal.ZERO.setScale(2); // FIXME fazer método
	}

	@Override
	public BigDecimal inadimplenciaAtual() {
		return cobrancaService.inadimplencia();
	}

	private BigDecimal[] receitaDespesaEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim) {
		BigDecimal[] resultado = new BigDecimal[2];
		if (!contas.isEmpty()) {
			resultado[0] = movimentoService.somaLancamentos(contas, inicio, fim, Boolean.FALSE);
			resultado[1] = movimentoService.somaLancamentos(contas, inicio, fim, Boolean.TRUE);
		}
		if (resultado[0] == null) {
			resultado[0] = BigDecimal.ZERO.setScale(2);
		}
		if (resultado[1] == null) {
			resultado[1] = BigDecimal.ZERO.setScale(2);
		}
		return resultado;
	}

	@Override
	public BigDecimal[] receitaDespesaMesAtual() {
		List<Conta> contas = contaService.listar();
		YearMonth mesAtual = YearMonth.from(LocalDate.now());
		return receitaDespesaEntre(contas, mesAtual.atDay(1), mesAtual.atEndOfMonth());
	}

	@Override
	public BigDecimal[] receitaDespesaRealizadaPeriodoAtual() {
		List<Conta> contas = contaService.listar();
		Periodo periodoAtual = periodoService.ler(LocalDate.now());
		if (periodoAtual != null) {
			return receitaDespesaEntre(contas, periodoAtual.getInicio(), periodoAtual.getFim());
		} else {
			BigDecimal[] resultado = new BigDecimal[2];
			resultado[0] = BigDecimal.ZERO.setScale(2);
			resultado[1] = BigDecimal.ZERO.setScale(2);
			return resultado;
		}
	}

	@Override
	public BigDecimal[] receitaDespesaOrcadaPeriodoAtual() {
		Periodo periodoAtual = periodoService.ler(LocalDate.now());
		BigDecimal[] resultado = new BigDecimal[2];
		if (periodoAtual != null) {
			resultado[0] = orcamentoService.somaOrcamentos(periodoAtual, TipoCategoria.R);
			resultado[1] = orcamentoService.somaOrcamentos(periodoAtual, TipoCategoria.D);
		}
		if (resultado[0] == null) {
			resultado[0] = BigDecimal.ZERO.setScale(2);
		}
		if (resultado[1] == null) {
			resultado[1] = BigDecimal.ZERO.setScale(2);
		}
		return resultado;

	}

	@Override
	public List<Movimento> lancamentosEntre(LocalDate inicio, LocalDate fim) {
		List<Conta> contas = contaService.listar();
		if (!contas.isEmpty()) {
			List<Movimento> lancamentos = new ArrayList<>();
			lancamentos.addAll(movimentoService.listarLancamentos(contas, inicio, fim));
			return lancamentos;
		}
		return new ArrayList<>();
	}

	@Override
	public BigDecimal[] saldosAposMovimentos(List<Movimento> movimentos, BigDecimal saldoInicial) {
		if (saldoInicial == null) {
			saldoInicial = BigDecimal.ZERO.setScale(2);
		}
		if (!movimentos.isEmpty()) {
			BigDecimal[] saldos = new BigDecimal[movimentos.size()];
			Movimento movimento = movimentos.get(0);
			// Preenche o primeiro saldo
			if (movimento.getReducao()) {
				saldos[0] = saldoInicial.subtract(movimento.getValor());
			} else {
				saldos[0] = saldoInicial.add(movimento.getValor());
			}
			// Preenche os outros saldos
			for (int i = 1; i < saldos.length; i++) {
				movimento = movimentos.get(i);
				if (movimento.getReducao()) {
					saldos[i] = saldos[i - 1].subtract(movimento.getValor());
				} else {
					saldos[i] = saldos[i - 1].add(movimento.getValor());
				}
			}
			return saldos;
		} else {
			BigDecimal[] vazio = new BigDecimal[1];
			vazio[0] = saldoInicial;
			return vazio;
		}
	}
}
