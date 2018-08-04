package app.condominio.service;

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

@Service
@Transactional
public class CobrancaServiceImpl implements CobrancaService {

	@Autowired
	private CobrancaDao cobrancaDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Cobranca entidade) {
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
		try {
			// Total deve bater com os campos totalizados
			if (0 != entidade.getTotal()
					.compareTo(entidade.getValor().subtract(entidade.getDesconto()).subtract(entidade.getAbatimento())
							.subtract(entidade.getOutrasDeducoes()).add(entidade.getJurosMora())
							.add(entidade.getMulta()).add(entidade.getOutrosAcrescimos()))) {
				validacao.rejectValue("total", "typeMismatch");
			}
		} catch (NullPointerException e) {
		}
		// Vencimento não pode ser antes da emissão
		if (entidade.getDataVencimento() != null && entidade.getDataVencimento().isBefore(entidade.getDataEmissao())) {
			validacao.rejectValue("dataVencimento", "typeMismatch");
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

	}

}
