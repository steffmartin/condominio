package app.condominio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.CobrancaDao;
import app.condominio.domain.Cobranca;
import app.condominio.domain.Condominio;
import app.condominio.domain.enums.MotivoEmissao;
import app.condominio.domain.enums.SituacaoCobranca;

@Service
@Transactional
public class CobrancaServiceImpl implements CobrancaService {

	@Autowired
	private CobrancaDao cobrancaDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Cobranca entidade) {
		padronizar(entidade);
		cobrancaDao.save(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Cobranca ler(Long id) {
		return cobrancaDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Cobranca> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<>();
		}
		return condominio.getCobrancas();
	}

	@Override
	public void editar(Cobranca entidade) {
		padronizar(entidade);
		cobrancaDao.save(entidade);
	}

	@Override
	public void excluir(Cobranca entidade) {
		cobrancaDao.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Cobranca entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdCobranca() == null) {
			if (entidade.getDataEmissao() != null && entidade.getMoradia() != null
					&& cobrancaDao.existsByNumeroAndParcelaAndDataEmissaoAndMoradiaAndCondominio(entidade.getNumero(),
							entidade.getParcela(), entidade.getDataEmissao(), entidade.getMoradia(),
							usuarioService.lerLogado().getCondominio())) {
				validacao.rejectValue("moradia", "Unique", new Object[] { 0, entidade.toString() }, null);
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			if (entidade.getDataEmissao() != null && entidade.getMoradia() != null
					&& cobrancaDao.existsByNumeroAndParcelaAndDataEmissaoAndMoradiaAndCondominioAndIdCobrancaNot(
							entidade.getNumero(), entidade.getParcela(), entidade.getDataEmissao(),
							entidade.getMoradia(), usuarioService.lerLogado().getCondominio(),
							entidade.getIdCobranca())) {
				validacao.rejectValue("moradia", "Unique", new Object[] { 0, entidade.toString() }, null);
			}
			if (entidade.getDataRecebimento() != null) {
				// Data de recebimento tem que ser maior/igual emissão
				if (entidade.getDataRecebimento().isBefore(entidade.getDataEmissao())) {
					validacao.rejectValue("dataRecebimento", "typeMismatch");
				}
				// Motivo baixa é obrigatório se tiver recebimento
				if (entidade.getMotivoBaixa() == null) {
					validacao.rejectValue("motivoBaixa", "NotNull");
				}
				// Motivo baixa é em branco se NÃO tiver recebimento
			} else if (entidade.getMotivoBaixa() != null) {
				validacao.rejectValue("motivoBaixa", "Null");
			}
			// Situação Cobrança não pode ser nula
			if (entidade.getSituacao() == null) {
				validacao.rejectValue("situacao", "NotNull");
			}
		}
		// VALIDAÇÕES EM AMBOS
		// Vencimento não pode ser antes da emissão
		if (entidade.getDataVencimento() != null && entidade.getDataVencimento().isBefore(entidade.getDataEmissao())) {
			validacao.rejectValue("dataVencimento", "typeMismatch");
		}
		if (entidade.getValor() != null && entidade.getTotal() != null) {
			// Valor não pode ser zero
			if (entidade.getValor().compareTo(BigDecimal.ZERO) == 0) {
				validacao.rejectValue("valor", "NotNull");
			} else {
				// Total deve bater com os campos totalizados
				BigDecimal teste = entidade.getValor();
				if (entidade.getDesconto() != null) {
					teste = teste.subtract(entidade.getDesconto());
				}
				if (entidade.getAbatimento() != null) {
					teste = teste.subtract(entidade.getAbatimento());
				}
				if (entidade.getOutrasDeducoes() != null) {
					teste = teste.subtract(entidade.getOutrasDeducoes());
				}
				if (entidade.getJurosMora() != null) {
					teste = teste.add(entidade.getJurosMora());
				}
				if (entidade.getMulta() != null) {
					teste = teste.add(entidade.getMulta());
				}
				if (entidade.getOutrosAcrescimos() != null) {
					teste = teste.add(entidade.getOutrosAcrescimos());
				}
				if (entidade.getTotal().compareTo(teste) != 0) {
					validacao.rejectValue("total", "typeMismatch");
				}
			}

		}

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Cobranca entidade) {
		if (entidade.getDataEmissao() == null) {
			entidade.setDataEmissao(LocalDate.now());
		}
		if (entidade.getMotivoEmissao() == null) {
			entidade.setMotivoEmissao(MotivoEmissao.O);
		}
		if (entidade.getSituacao() == null) {
			entidade.setSituacao(SituacaoCobranca.N);
		}
		if (entidade.getCondominio() == null) {
			entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		}
		if (entidade.getDesconto() == null) {
			entidade.setDesconto(BigDecimal.ZERO);
		}
		if (entidade.getAbatimento() == null) {
			entidade.setAbatimento(BigDecimal.ZERO);
		}
		if (entidade.getOutrasDeducoes() == null) {
			entidade.setOutrasDeducoes(BigDecimal.ZERO);
		}
		if (entidade.getJurosMora() == null) {
			entidade.setJurosMora(BigDecimal.ZERO);
		}
		if (entidade.getMulta() == null) {
			entidade.setMulta(BigDecimal.ZERO);
		}
		if (entidade.getOutrosAcrescimos() == null) {
			entidade.setOutrosAcrescimos(BigDecimal.ZERO);
		}
		if (entidade.getPercentualJurosMes() == null) {
			entidade.setPercentualJurosMes(new Float(0));
		}
		if (entidade.getPercentualMulta() == null) {
			entidade.setPercentualMulta(new Float(0));
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal inadimplencia() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null || condominio.getCobrancas().isEmpty()) {
			return BigDecimal.ZERO.setScale(2);
		} else {
			return cobrancaDao.sumTotalByCondominioAndDataVencimentoBeforeAndDataRecebimentoIsNull(condominio,
					LocalDate.now());
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Cobranca> listarInadimplencia() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		List<Cobranca> lista = new ArrayList<>();
		if (condominio != null && !condominio.getCobrancas().isEmpty()) {
			lista.addAll(cobrancaDao
					.findAllByCondominioAndDataVencimentoBeforeAndDataRecebimentoIsNullOrderByMoradia_Bloco_SiglaAscMoradia_SiglaAsc(
							condominio, LocalDate.now()));
		}
		return lista;
	}

}
