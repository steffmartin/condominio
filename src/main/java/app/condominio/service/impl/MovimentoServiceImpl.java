package app.condominio.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import app.condominio.service.ContaService;
import app.condominio.service.MovimentoService;
import app.condominio.service.PeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.LancamentoDao;
import app.condominio.dao.MovimentoDao;
import app.condominio.domain.Categoria;
import app.condominio.domain.Conta;
import app.condominio.domain.Lancamento;
import app.condominio.domain.Movimento;
import app.condominio.domain.Periodo;
import app.condominio.domain.Subcategoria;
import app.condominio.domain.Transferencia;
import app.condominio.domain.enums.TipoCategoria;

@Service
@Transactional
public class MovimentoServiceImpl implements MovimentoService {

	@Autowired
	private MovimentoDao movimentoDao;

	@Autowired
	private LancamentoDao lancamentoDao;

	@Autowired
	private ContaService contaService;

	@Autowired
	private PeriodoService periodoService;

	@Override
	public void salvar(Movimento entidade) {
		if (entidade.getIdMovimento() == null) {
			padronizar(entidade);
			List<Movimento> listaSalvar = new ArrayList<>();
			Transferencia contrapartida;
			if (entidade instanceof Lancamento) {
				((Lancamento) entidade).setPeriodo(periodoService.ler(entidade.getData()));
				if (((Lancamento) entidade).getSubcategoria().getCategoriaPai().getTipo().equals(TipoCategoria.D)) {
					entidade.setReducao(Boolean.TRUE);
				} else {
					entidade.setReducao(Boolean.FALSE);
				}
			} else if (entidade instanceof Transferencia) {
				entidade.setReducao(Boolean.TRUE);
				// LATER ver se tem forma mais prática de criar espelho do movimento
				contrapartida = new Transferencia();
				contrapartida.setData(entidade.getData());
				contrapartida.setValor(entidade.getValor());
				contrapartida.setDocumento(entidade.getDocumento());
				contrapartida.setDescricao(entidade.getDescricao());
				contrapartida.setConta(((Transferencia) entidade).getContaInversa());
				contrapartida.setContaInversa(entidade.getConta());
				contrapartida.setReducao(Boolean.FALSE);
				contrapartida.setMovimentoInverso(entidade);
				((Transferencia) entidade).setMovimentoInverso(contrapartida);
				listaSalvar.add(contrapartida);
			}
			listaSalvar.add(entidade);
			movimentoDao.saveAll(listaSalvar);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Movimento ler(Long id) {
		return movimentoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Movimento> listar() {
		return movimentoDao.findAllByContaInOrderByDataDesc(contaService.listar());
	}

	@Override
	public Page<Movimento> listarPagina(Pageable pagina) {
		return movimentoDao.findAllByContaInOrderByDataDesc(contaService.listar(), pagina);
	}

	@Override
	public void editar(Movimento entidade) {
		padronizar(entidade);
		List<Movimento> listaSalvar = new ArrayList<>();
		if (entidade instanceof Lancamento) {
			((Lancamento) entidade).setPeriodo(periodoService.ler(entidade.getData()));
			if (((Lancamento) entidade).getSubcategoria().getCategoriaPai().getTipo().equals(TipoCategoria.D)) {
				entidade.setReducao(Boolean.TRUE);
			} else {
				entidade.setReducao(Boolean.FALSE);
			}
		} else if (entidade instanceof Transferencia) {
			((Transferencia) entidade).getMovimentoInverso().setData(entidade.getData());
			((Transferencia) entidade).getMovimentoInverso().setValor(entidade.getValor());
			((Transferencia) entidade).getMovimentoInverso().setDocumento(entidade.getDocumento());
			((Transferencia) entidade).getMovimentoInverso().setDescricao(entidade.getDescricao());
			((Transferencia) entidade).getMovimentoInverso().setConta(((Transferencia) entidade).getContaInversa());
			((Transferencia) ((Transferencia) entidade).getMovimentoInverso()).setContaInversa(entidade.getConta());
			((Transferencia) entidade).getMovimentoInverso().setReducao(!((Transferencia) entidade).getReducao());
			((Transferencia) ((Transferencia) entidade).getMovimentoInverso()).setMovimentoInverso(entidade);
			listaSalvar.add(((Transferencia) entidade).getMovimentoInverso());
		}
		listaSalvar.add(entidade);
		movimentoDao.saveAll(listaSalvar);
	}

	@Override
	public void excluir(Movimento entidade) {
		List<Movimento> listaDeletar = new ArrayList<>();
		if (entidade instanceof Transferencia) {
			listaDeletar.add(((Transferencia) entidade).getMovimentoInverso());
		}
		listaDeletar.add(entidade);
		movimentoDao.deleteAll(listaDeletar);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Movimento entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		// if (entidade.getIdMovimento() == null) {
		//
		// }
		// // VALIDAÇÕES NA ALTERAÇÃO
		// else {
		//
		// }
		// VALIDAÇÕES EM AMBOS
		// Só permitir lançamento se o período existir e estiver aberto
		if (entidade.getData() != null && entidade instanceof Lancamento) {
			if (!periodoService.haPeriodo(entidade.getData())) {
				validacao.rejectValue("data", "Inexistente");
			} else if (periodoService.ler(entidade.getData()).getEncerrado()) {
				validacao.rejectValue("data", "Final");
			}
		}
		// Não permitir transferência para conta igual
		if (entidade.getConta() != null && entidade instanceof Transferencia
				&& entidade.getConta().equals(((Transferencia) entidade).getContaInversa())) {
			validacao.rejectValue("contaInversa", "Conflito");
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Movimento entidade) {
		if (entidade.getData() == null) {
			entidade.setData(LocalDate.now());
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim, Boolean reducao) {
		if (!contas.isEmpty()) {
			return lancamentoDao.sumValorByContaInAndDataBetweenAndReducao(contas, inicio, fim, reducao);
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim,
			Subcategoria subcategoria) {
		if (!contas.isEmpty()) {
			return lancamentoDao.sumValorByContaInAndDataBetweenAndSubcategoria(contas, inicio, fim, subcategoria);
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaLancamentosDesde(Collection<Conta> contas, LocalDate inicio, Boolean reducao) {
		if (!contas.isEmpty()) {
			return lancamentoDao.sumValorByContaInAndDataGreaterThanEqualAndReducao(contas, inicio, reducao);
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Lancamento> listarLancamentosEntre(Collection<Conta> contas, LocalDate inicio, LocalDate fim) {
		if (!contas.isEmpty()) {
			return lancamentoDao.findAllByContaInAndDataBetweenOrderByDataAsc(contas, inicio, fim);
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaLancamentosPeriodo(Collection<Conta> contas, Periodo periodo, Subcategoria subcategoria) {
		if (!contas.isEmpty() && periodo != null && subcategoria != null) {
			return lancamentoDao.sumValorByContaInAndPeriodoAndSubcategoria(contas, periodo, subcategoria);
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaLancamentosPeriodo(Collection<Conta> contas, Periodo periodo, Categoria categoria) {
		if (!contas.isEmpty() && periodo != null && categoria != null) {
			return lancamentoDao.sumValorByContaInAndPeriodoAndSubcategoria_CategoriaPai_OrdemStartingWith(contas,
					periodo, categoria.getOrdem());
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

}
