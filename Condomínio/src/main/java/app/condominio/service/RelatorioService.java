package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import app.condominio.domain.Categoria;
import app.condominio.domain.Cobranca;
import app.condominio.domain.Moradia;
import app.condominio.domain.Movimento;
import app.condominio.domain.Periodo;
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
	 *         respectiva Subcategoria dentro das datas informadas no parâmetro.
	 *         Retorna somente Subcategorias com soma diferente de zero. Nunca
	 *         retorna nulo, se não houverem entradas, retorna um mapa vazio.
	 */
	public Map<Subcategoria, BigDecimal> somasPorTipoEntre(LocalDate inicio, LocalDate fim,
			TipoCategoria tipoCategoria);

	/**
	 * @return Retorna um mapa do tipo Map{@literal <}Moradia,List{@literal
	 *         <}Cobranca{@literal >>}. Cada entrada do mapa é composta por uma
	 *         Moradia como chave e uma lista de Cobranca como valor. Esta lista
	 *         contém todas as Cobranças vencidas até a data atual da respectiva
	 *         Moradia. Retorna somente Moradias com uma lista não vazia. Nunca
	 *         retorna nulo, se não houverem entradas, retorna um mapa vazio.
	 */
	public SortedMap<Moradia, List<Cobranca>> inadimplenciaAtualDetalhada();

	/**
	 * @param map
	 *            Um mapa do tipo Map{@literal <}Moradia,List{@literal
	 *         <}Cobranca{@literal >>} para ser somado
	 * @return Retorna um mapa do tipo Map{@literal <}Moradia,BigDecimal{@literal >}
	 *         com as mesmas chaves do mapa fornecido no parâmetro. Cada valor
	 *         corresponde à soma das Cobranças da respectiva Moradia. Nunca retorna
	 *         nulo, se não houverem entradas, retorna um mapa vazio.
	 */
	public Map<Moradia, BigDecimal> somaCobrancas(Map<Moradia, List<Cobranca>> map);

	/**
	 * @param periodo
	 *            Um Periodo para pesquisa
	 * @return Retorna um mapa do tipo
	 *         Map{@literal <}Subcategoria,BigDecimal[]{@literal >} tendo como chave
	 *         uma Subcategoria. Retorna somente Subcategorias que tiveram, no
	 *         Período fornecido por parâmetro, valores Orçados ou Realizados. O
	 *         valor é um vetor de BigDecimal[] com duas posições. A posição [0] é o
	 *         valor orçado da respectiva Subcategoria naquele Período, e a posição
	 *         [1] é a soma dos Lançamentos realizados da respectiva Subcategoria
	 *         naquele Período, em todas as Contas. Nunca retorna nulo, se não
	 *         houverem entradas, retorna um mapa vazio.
	 */
	public Map<Subcategoria, BigDecimal[]> somaOrcadoRealizadoSubcategorias(Periodo periodo);

	/**
	 * @param periodo
	 *            Um Periodo para pesquisa
	 * @return Retorna um mapa do tipo
	 *         Map{@literal <}Categoria,BigDecimal[]{@literal >} tendo como chave
	 *         uma Categoria. Retorna somente Categorias que tiveram, no Período
	 *         fornecido por parâmetro, valores Orçados ou Realizados em alguma de
	 *         suas Subcategorias. O valor é um vetor de BigDecimal[] com duas
	 *         posições. A posição [0] é a soma do valor orçado das Subcategorias da
	 *         respectiva Categoria naquele Período, e a posição [1] é a soma dos
	 *         Lançamentos realizados das Subcategorias da respectiva Categoria
	 *         naquele Período, em todas as Contas. Nunca retorna nulo, se não
	 *         houverem entradas, retorna um mapa vazio.
	 */
	public Map<Categoria, BigDecimal[]> somaOrcadoRealizadoCategorias(Periodo periodo);

}
