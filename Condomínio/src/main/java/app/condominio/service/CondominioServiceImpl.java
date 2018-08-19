package app.condominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.dao.CondominioDao;
import app.condominio.domain.Condominio;
import app.condominio.domain.Usuario;

@Service
@Transactional
public class CondominioServiceImpl implements CondominioService {

	// LATER ao ler/editar/excluir, tratar casos do usuário alterar o ID na URL ou
	// fonte e enxergar entidades de outro usuário (todas as classes)

	@Autowired
	private CondominioDao condominioDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Condominio condominio) {
		condominioDao.save(condominio);

		// Atualizar o ID do condomínio no cadastro do síndico
		Usuario sindico = usuarioService.lerLogado();
		sindico.setCondominio(condominio);
		usuarioService.editar(sindico);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Condominio ler() {
		return usuarioService.lerLogado().getCondominio();
	}

	@Override
	public void editar(Condominio condominio) {
		condominioDao.save(condominio);
	}

	@Override
	public void excluir(Condominio condominio) {
		condominioDao.delete(condominio);
	}

}
