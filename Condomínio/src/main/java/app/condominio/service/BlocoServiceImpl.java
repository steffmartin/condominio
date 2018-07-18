package app.condominio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.dao.BlocoDao;
import app.condominio.domain.Bloco;

@Service
@Transactional
public class BlocoServiceImpl implements BlocoService {
	
	//LATER ao ler/editar/excluir, tratar casos do usuário alterar o ID na URL ou fonte e enxergar entidades de outro usuário

	@Autowired
	private BlocoDao blocoDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Bloco bloco) {
		bloco.setCondominio(usuarioService.lerLogado().getCondominio());
		blocoDao.save(bloco);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Bloco ler(Long id) {
		return blocoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Bloco> listar() {
		return usuarioService.lerLogado().getCondominio().getBlocos();
	}

	@Override
	public void editar(Bloco bloco) {
		blocoDao.save(bloco);
	}

	@Override
	public void excluir(Bloco bloco) {
		blocoDao.delete(bloco);
	}

}
