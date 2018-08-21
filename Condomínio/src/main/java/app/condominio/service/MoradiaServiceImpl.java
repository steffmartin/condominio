package app.condominio.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.MoradiaDao;
import app.condominio.domain.Moradia;
import app.condominio.domain.Relacao;

@Service
@Transactional
public class MoradiaServiceImpl implements MoradiaService {

	@Autowired
	private MoradiaDao moradiaDao;

	@Autowired
	private BlocoService blocoService;

	@Override
	public void salvar(Moradia entidade) {
		moradiaDao.save(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Moradia ler(Long id) {
		return moradiaDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Moradia> listar() {
		return moradiaDao.findAllByBlocoInOrderByBloco_SiglaAscSiglaAsc(blocoService.listar());
	}

	@Override
	public void editar(Moradia entidade) {
		moradiaDao.save(entidade);
		// FIXME Não está excluindo as relações quando não tem mais nenhuma relação
	}

	@Override
	public void setRelacaoMoradia(Moradia entidade) {
		Iterator<Relacao> it = entidade.getRelacoes().iterator();
		while (it.hasNext()) {
			Relacao relacao = it.next();
			if (relacao.getMoradia() == null) {
				relacao.setMoradia(entidade);
			}
		}
	}

	@Override
	public void excluir(Moradia entidade) {
		moradiaDao.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Moradia entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdMoradia() == null) {
			// Sigla não pode repetir
			if (entidade.getBloco() != null
					&& moradiaDao.existsBySiglaAndBloco(entidade.getSigla(), entidade.getBloco())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// Sigla não pode repetir
			if (entidade.getBloco() != null && moradiaDao.existsBySiglaAndBlocoAndIdMoradiaNot(entidade.getSigla(),
					entidade.getBloco(), entidade.getIdMoradia())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
		// Em uma relação é obrigatório ter a pessoa
		List<Relacao> relacoes = entidade.getRelacoes();
		for (int i = 0; i < relacoes.size(); i++) {
			if (relacoes.get(i).getPessoa() == null) {
				validacao.rejectValue("relacoes[" + i + "].pessoa", "NotNull");
			}
		}
	}

}
