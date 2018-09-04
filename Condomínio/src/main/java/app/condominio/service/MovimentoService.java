package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import app.condominio.domain.Conta;
import app.condominio.domain.Lancamento;
import app.condominio.domain.Movimento;

public interface MovimentoService extends CrudService<Movimento, Long> {

	public BigDecimal somaLancamentos(Collection<Conta> contas, LocalDate inicio, LocalDate fim, Boolean reducao);

	public List<Lancamento> listarLancamentos(Collection<Conta> contas, LocalDate inicio, LocalDate fim);

}
