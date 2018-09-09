package app.condominio.service;

import java.math.BigDecimal;
import java.util.List;

import app.condominio.domain.Cobranca;

public interface CobrancaService extends CrudService<Cobranca, Long> {

	/**
	 * @return Retorna um BigDecimal com o valor total da inadimplência do
	 *         Condomínio na data atual (considera o valor total da Cobrança, com
	 *         acréscimos e deduções). Nunca retorna nulo, se não houver
	 *         inadimplência, retorna BigDecimal.ZERO.
	 */
	public BigDecimal inadimplencia();

	/**
	 * @return Retorna uma lista do tipo List{@literal <}Cobranca{@literal >} com
	 *         todas as Cobrancas do Condomínio vencidas na data atual (considera o
	 *         valor total da Cobrança, com acréscimos e deduções). Nunca retorna
	 *         nulo, se não houver inadimplência, retorna uma lista vazia.
	 */
	public List<Cobranca> listarInadimplencia();

}
