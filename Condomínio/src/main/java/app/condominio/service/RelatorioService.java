package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import app.condominio.domain.Movimento;
import app.condominio.domain.Subcategoria;
import app.condominio.domain.enums.TipoCategoria;

public interface RelatorioService {

	/**
	 * @return Retorna um BigDecimal com a soma do saldo de todas as Contas do
	 *         Condomínio. Nunca retorna nulo, se não houver contas, retorna
	 *         BigDecimal.ZERO.
	 */
	public BigDecimal saldoAtualTodasContas();

	/**
	 * @param data
	 *            Um dia para pesquisa.
	 * @return Retorna um BigDecimal com o saldo de todas as Contas do Condomínio no
	 *         início do dia passado no parâmetro. Nunca retorna nulo, se não houver
	 *         contas, retorna BigDecimal.ZERO.
	 */
	public BigDecimal saldoInicialTodasContasEm(LocalDate data);

	/**
	 * @param data
	 *            Um dia para pesquisa.
	 * @return Retorna um BigDecimal com o saldo de todas as Contas do Condomínio no
	 *         fim do dia passado no parâmetro. Nunca retorna nulo, se não houver
	 *         contas, retorna BigDecimal.ZERO.
	 */
	public BigDecimal saldoFinalTodasContasEm(LocalDate data);

	/**
	 * @return Retorna um BigDecimal com o valor total da inadimplência do
	 *         Condomínio na data atual (considera o valor total da Cobrança, com
	 *         acréscimos e deduções). Nunca retorna nulo, se não houver
	 *         inadimplência, retorna BigDecimal.ZERO.
	 */
	public BigDecimal inadimplenciaAtual();

	/**
	 * @return Retorna um vetor de BigDecimal[] com duas posições. A posição [0] é a
	 *         soma dos lançamentos de receitas do mês atual, e a posição [1] é a
	 *         soma dos lançamentos de despesas do mês atual. Nunca retorna nulo, se
	 *         não houver lançamentos, retorna BigDecimal.ZERO na respectiva posição
	 *         do vetor.
	 */
	public BigDecimal[] receitaDespesaMesAtual();

	/**
	 * @param inicio
	 *            Data inicial para pesquisa
	 * @param fim
	 *            Data final para pesquisa
	 * @return Retorna um vetor de BigDecimal[] com duas posições. A posição [0] é a
	 *         soma dos lançamentos de receitas dentro das datas informadas no
	 *         parâmetro, e a posição [1] é a soma dos lançamentos de despesas
	 *         dentro das datas informadas no parâmetro. Nunca retorna nulo, se não
	 *         houver lançamentos, retorna BigDecimal.ZERO na respectiva posição do
	 *         vetor.
	 */
	public BigDecimal[] receitaDespesaEntre(LocalDate inicio, LocalDate fim);

	/**
	 * @return Retorna um vetor de BigDecimal[] com duas posições. A posição [0] é a
	 *         soma dos lançamentos de receitas realizadas do Período atual, e a
	 *         posição [1] é a soma dos lançamentos de despesas realizadas do
	 *         Período atual. Nunca retorna nulo, se não houver lançamentos, retorna
	 *         BigDecimal.ZERO na respectiva posição do vetor.
	 */
	public BigDecimal[] receitaDespesaRealizadaPeriodoAtual();

	/**
	 * @return Retorna um vetor de BigDecimal[] com duas posições. A posição [0] é a
	 *         soma dos lançamentos de receitas orçadas do Período atual, e a
	 *         posição [1] é a soma dos lançamentos de despesas orçadas do Período
	 *         atual. Nunca retorna nulo, se não houver lançamentos, retorna
	 *         BigDecimal.ZERO na respectiva posição do vetor.
	 */
	public BigDecimal[] receitaDespesaOrcadaPeriodoAtual();

	/**
	 * @param inicio
	 *            Data inicial para pesquisa
	 * @param fim
	 *            Data final para pesquisa
	 * @return Retorna uma lista do tipo List{@literal <}Movimento{@literal >} com
	 *         Lançamentos existentes em todas as Contas dentro das datas informadas
	 *         no parâmetro. Nunca retorna nulo, se não houverem Contas ou
	 *         Lançamentos, retorna uma lista vazia.
	 */
	public List<Movimento> lancamentosEntre(LocalDate inicio, LocalDate fim);

	/**
	 * @param movimentos
	 *            Uma lista do tipo List{@literal <}Movimento{@literal >}
	 * @param saldoInicial
	 *            Um BigDecimal para ser considerado como saldo inicial
	 * @return Retorna um vetor de BigDecimal[] com tamanho igual ao número de
	 *         elementos na lista passada no parâmetro, contendo em cada enésima
	 *         posição o saldo após processado o enésimo Movimento, partindo do
	 *         saldo inicial informado no parâmetro. Nunca retorna nulo, se a lista
	 *         passada no parâmetro estiver vazia, retorna um vetor com uma posição
	 *         com valor BigDecimal.ZERO.
	 */
	public BigDecimal[] saldosAposMovimentos(List<Movimento> movimentos, BigDecimal saldoInicial);

	/**
	 * @param inicio
	 *            Data inicial para pesquisa
	 * @param fim
	 *            Data final para pesquisa
	 * @param tipoCategoria
	 *            O tipo da Categoria pai da Subcategoria a ser buscada
	 * @return Retorna um mapa do tipo Map{@literal <}Subcategoria,
	 *         BigDecimal{@literal >}. Cada entrada do mapa é composta por uma
	 *         Subcategoria como chave e um BigDecimal como valor. O valor
	 *         representa a soma de todos os Lançamentos existentes para a
	 *         Subcategoria chave dentro das datas informadas no parâmetro. Retorna
	 *         somente Subcategorias com soma diferente de zero. Nunca retorna nulo,
	 *         se não houverem entradas, retorna um mapa vazio.
	 */
	public Map<Subcategoria, BigDecimal> somasPorTipoEntre(LocalDate inicio, LocalDate fim,
			TipoCategoria tipoCategoria);

}
