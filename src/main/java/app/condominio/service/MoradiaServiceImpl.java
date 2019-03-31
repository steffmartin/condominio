package app.condominio.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.MoradiaDao;
import app.condominio.domain.Moradia;
import app.condominio.domain.Pessoa;
import app.condominio.domain.Relacao;
import app.condominio.domain.enums.TipoRelacao;

@Service
@Transactional
public class MoradiaServiceImpl implements MoradiaService {

	@Autowired
	private MoradiaDao moradiaDao;

	@Autowired
	private BlocoService blocoService;

	@Override
	public void salvar(Moradia entidade) {
		if (entidade.getIdMoradia() == null) {
			padronizar(entidade);
			moradiaDao.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Moradia ler(Long id) {
		return moradiaDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Moradia> listar() {
		return moradiaDao.findAllByBlocoInOrderByBlocoAscSiglaAsc(blocoService.listar());
	}

	@Override
	public Page<Moradia> listarPagina(Pageable pagina) {
		return moradiaDao.findAllByBlocoInOrderByBlocoAscSiglaAsc(blocoService.listar(), pagina);
	}

	@Override
	public void editar(Moradia entidade) {
		padronizar(entidade);
		moradiaDao.save(entidade);
		// FIXME Não está excluindo as relações quando não tem mais nenhuma relação
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
		// Validar relação
		List<Relacao> relacoes = entidade.getRelacoes();
		Set<Pessoa> pessoas = new HashSet<>();
		for (int i = 0; i < relacoes.size(); i++) {
			// Em uma relação é obrigatório ter a pessoa
			if (relacoes.get(i).getPessoa() == null) {
				validacao.rejectValue("relacoes[" + i + "].pessoa", "NotNull");
			} else {
				if (pessoas.contains(relacoes.get(i).getPessoa())) {
					validacao.rejectValue("relacoes[" + i + "].pessoa", "Unique");
				} else {
					pessoas.add(relacoes.get(i).getPessoa());
				}
				// LATER permitir repetir pessoa em períodos diferentes, tem que mudar BD
			}
			// Se for proprietário é obrigatório ter o percentual
			if (TipoRelacao.P.equals(relacoes.get(i).getTipo())
					&& (relacoes.get(i).getParticipacaoDono() == null || relacoes.get(i).getParticipacaoDono() == 0)) {
				validacao.rejectValue("relacoes[" + i + "].participacaoDono", "NotNull");
			}
			// Se tiver data de saída não pode ser menor que a entrada
			if (relacoes.get(i).getDataEntrada() != null && relacoes.get(i).getDataSaida() != null
					&& relacoes.get(i).getDataSaida().isBefore(relacoes.get(i).getDataEntrada())) {
				validacao.rejectValue("relacoes[" + i + "].dataSaida", "typeMismatch");
			}
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Moradia entidade) {
		if (entidade.getFracaoIdeal() == null) {
			entidade.setFracaoIdeal(new Float(0));
		}
		if (entidade.getArea() == null) {
			entidade.setArea(new Float(0));
		}
		if (entidade.getVagas() == null) {
			entidade.setVagas(0);
		}
		Iterator<Relacao> it = entidade.getRelacoes().iterator();
		while (it.hasNext()) {
			Relacao relacao = it.next();
			if (relacao.getMoradia() == null) {
				relacao.setMoradia(entidade);
			}
			if (relacao.getParticipacaoDono() == null) {
				relacao.setParticipacaoDono(new Float(0));
			}
		}

	}

}
