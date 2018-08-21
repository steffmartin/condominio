package app.condominio.service;

import java.math.BigDecimal;
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
		entidade.setSituacao(SituacaoCobranca.N);
		entidade.setCondominio(usuarioService.lerLogado().getCondominio());
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
			// TODO validação: não pode repetir numero+parcela+emissao+moradia
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
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

}
