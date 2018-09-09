package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.domain.Cobranca;
import app.condominio.domain.Conta;
import app.condominio.domain.Moradia;
import app.condominio.domain.Movimento;
import app.condominio.domain.Periodo;
import app.condominio.domain.Subcategoria;
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

	@Autowired
	SubcategoriaService subcategoriaService;

	@Override
	public BigDecimal saldoAtualTodasContas() {
		return contaService.saldoAtual();
	}

	@Override
	public BigDecimal saldoInicialTodasContasEm(LocalDate data) {
		BigDecimal saldo = contaService.saldoAtual();
		BigDecimal[] lancamentos = receitaDespesaDesde(contaService.listar(), data);
		return saldo.subtract(lancamentos[0]).add(lancamentos[1]);
	}

	@Override
	public BigDecimal saldoFinalTodasContasEm(LocalDate data) {
		return saldoInicialTodasContasEm(data.plusDays(1));
	}

	@Override
	public BigDecimal inadimplenciaAtual() {
		return cobrancaService.inadimplencia();
	}

	private BigDecimal[] receitaDespesaEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim) {
		BigDecimal[] resultado = new BigDecimal[2];
		if (!contas.isEmpty()) {
			resultado[0] = movimentoService.somaLancamentosEntre(contas, inicio, fim, Boolean.FALSE);
			resultado[1] = movimentoService.somaLancamentosEntre(contas, inicio, fim, Boolean.TRUE);
		}
		if (resultado[0] == null) {
			resultado[0] = BigDecimal.ZERO.setScale(2);
		}
		if (resultado[1] == null) {
			resultado[1] = BigDecimal.ZERO.setScale(2);
		}
		return resultado;
	}

	private BigDecimal[] receitaDespesaDesde(Collection<Conta> contas, LocalDate inicio) {
		BigDecimal[] resultado = new BigDecimal[2];
		if (!contas.isEmpty()) {
			resultado[0] = movimentoService.somaLancamentosDesde(contas, inicio, Boolean.FALSE);
			resultado[1] = movimentoService.somaLancamentosDesde(contas, inicio, Boolean.TRUE);
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
	public BigDecimal[] receitaDespesaEntre(LocalDate inicio, LocalDate fim) {
		List<Conta> contas = contaService.listar();
		return receitaDespesaEntre(contas, inicio, fim);
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
			lancamentos.addAll(movimentoService.listarLancamentosEntre(contas, inicio, fim));
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

	@Override
	public Map<Subcategoria, BigDecimal> somasPorTipoEntre(LocalDate inicio, LocalDate fim,
			TipoCategoria tipoCategoria) {
		Map<Subcategoria, BigDecimal> map = new HashMap<>();
		List<Conta> contas = contaService.listar();
		if (!contas.isEmpty()) {
			List<Subcategoria> subcategorias;
			if (TipoCategoria.R.equals(tipoCategoria)) {
				subcategorias = subcategoriaService.listarReceitas();
			} else if (TipoCategoria.D.equals(tipoCategoria)) {
				subcategorias = subcategoriaService.listarDespesas();
			} else {
				return map;
			}
			for (Subcategoria subcategoria : subcategorias) {
				BigDecimal soma = movimentoService.somaLancamentosEntre(contas, inicio, fim, subcategoria);
				if (soma != null && soma.compareTo(BigDecimal.ZERO) != 0) {
					map.put(subcategoria, soma);
				}
			}
		}
		return map;
	}

	@Override
	public SortedMap<Moradia, List<Cobranca>> inadimplenciaAtualDetalhada() {
		SortedMap<Moradia, List<Cobranca>> map = new TreeMap<>();
		List<Cobranca> inadimplencia = cobrancaService.listarInadimplencia();
		for (Cobranca cobranca : inadimplencia) {
			List<Cobranca> lista;
			if (map.containsKey(cobranca.getMoradia())) {
				lista = map.get(cobranca.getMoradia());
			} else {
				lista = new ArrayList<>();
			}
			lista.add(cobranca);
			map.put(cobranca.getMoradia(), lista);
		}
		return map;
	}

	@Override
	public Map<Moradia, BigDecimal> somaCobrancas(Map<Moradia, List<Cobranca>> map) {
		Map<Moradia, BigDecimal> mapa = new HashMap<>();
		if (!map.isEmpty()) {
			for (Map.Entry<Moradia, List<Cobranca>> entrada : map.entrySet()) {
				BigDecimal soma = BigDecimal.ZERO;
				for (Cobranca cobranca : entrada.getValue()) {
					soma = soma.add(cobranca.getTotal());
				}
				mapa.put(entrada.getKey(), soma);
			}
		}
		return mapa;
	}
}
