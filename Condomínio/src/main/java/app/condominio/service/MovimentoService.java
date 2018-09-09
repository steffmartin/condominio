package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import app.condominio.domain.Conta;
import app.condominio.domain.Lancamento;
import app.condominio.domain.Movimento;
import app.condominio.domain.Subcategoria;

public interface MovimentoService extends CrudService<Movimento, Long> {

	public BigDecimal somaLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim, Boolean reducao);

	public BigDecimal somaLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim,
			Subcategoria subcategoria);

	public BigDecimal somaLancamentosDesde(Collection<Conta> contas, LocalDate inicio, Boolean reducao);

	public List<Lancamento> listarLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim);

}
