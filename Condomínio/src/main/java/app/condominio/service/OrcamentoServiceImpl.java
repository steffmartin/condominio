package app.condominio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.OrcamentoDao;
import app.condominio.domain.Condominio;
import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;

@Service
@Transactional
public class OrcamentoServiceImpl implements OrcamentoService {

	@Autowired
	private OrcamentoDao orcamentoDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Orcamento entidade) {
		orcamentoDao.save(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Orcamento ler(Long id) {
		return orcamentoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Orcamento> listar() {
		List<Orcamento> orcamentos = new ArrayList<>();
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio != null) {
			for (Periodo periodo : condominio.getPeriodos()) {
				orcamentos.addAll(periodo.getOrcamentos());
			}
		}
		return orcamentos;
	}

	@Override
	public void editar(Orcamento entidade) {
		orcamentoDao.save(entidade);

	}

	@Override
	public void excluir(Orcamento entidade) {
		orcamentoDao.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Orcamento entidade, BindingResult validacao) {
		// TODO Auto-generated method stub

	}

}
