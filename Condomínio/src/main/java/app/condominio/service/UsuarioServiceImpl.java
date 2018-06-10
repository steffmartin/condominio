package app.condominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.dao.UsuarioDao;
import app.condominio.domain.Autorizacao;
import app.condominio.domain.Usuario;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void salvar(Usuario usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuarioDao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Usuario ler(String username) {
		return usuarioDao.findByUsername(username);
	}

	@Override
	public void editar(Usuario usuario) {
		usuarioDao.save(usuario);

	}

	@Override
	public void excluir(Usuario usuario) {
		usuarioDao.delete(usuario);

	}

	@Override
	public void salvarSindico(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.SINDICO);
		usuario.setAtivo(true);
		salvar(usuario);
	}

	@Override
	public void salvarMorador(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.MORADOR);
		usuario.setAtivo(true);
		salvar(usuario);
	}

}
