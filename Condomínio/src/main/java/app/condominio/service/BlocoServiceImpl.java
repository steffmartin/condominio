package app.condominio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.BlocoDao;
import app.condominio.domain.Bloco;
import app.condominio.domain.Condominio;

@Service
@Transactional
public class BlocoServiceImpl implements BlocoService {

	@Autowired
	private BlocoDao blocoDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Bloco entidade) {
		entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		blocoDao.save(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Bloco ler(Long id) {
		return blocoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Bloco> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<Bloco>();
		}
		return condominio.getBlocos();
	}

	@Override
	public void editar(Bloco entidade) {
		blocoDao.save(entidade);
	}

	@Override
	public void excluir(Bloco entidade) {
		blocoDao.delete(entidade);
	}

	@Override
	public void validar(Bloco entidade, BindingResult validacao) {
		// LATER ver se haverá validação de bloco a fazer
		
	}
}
