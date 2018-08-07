package app.condominio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.LancamentoDao;
import app.condominio.domain.Condominio;
import app.condominio.domain.Lancamento;
import app.condominio.domain.Periodo;

@Service
@Transactional
public class LancamentoServiceImpl implements LancamentoService {

	@Autowired
	private LancamentoDao lancamentoDao;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PeriodoService periodoService;

	@Override
	public void salvar(Lancamento entidade) {
		entidade.setPeriodo(periodoService.ler(entidade.getData()));
		lancamentoDao.save(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lancamento ler(Long id) {
		return lancamentoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Lancamento> listar() {
		List<Lancamento> lancamentos = new ArrayList<>();
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio != null) {
			for (Periodo periodo : condominio.getPeriodos()) {
				lancamentos.addAll(periodo.getLancamentos());
			}
		}
		return lancamentos;
	}

	@Override
	public void editar(Lancamento entidade) {
		lancamentoDao.save(entidade);

	}

	@Override
	public void excluir(Lancamento entidade) {
		lancamentoDao.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Lancamento entidade, BindingResult validacao) {
		// Só permitir lançamento se o período existir
		if (!periodoService.haPeriodo(entidade.getData())) {
			validacao.rejectValue("data", "Inexistente");
		}

	}

}
