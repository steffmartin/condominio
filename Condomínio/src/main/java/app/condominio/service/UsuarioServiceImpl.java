package app.condominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	private JavaMailSender emailSender;
	// LATER Criar classe EmailService

	@Autowired
	private PasswordEncoder passwordEncoder;

	private void salvar(Usuario usuario) {
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

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean existe(String username) {
		return usuarioDao.existsByUsername(username);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean redefinirSenha(String username) {
		Usuario usuario = ler(username);
		if (usuario != null) {
			SimpleMailMessage mensagem = new SimpleMailMessage();
			mensagem.setTo(usuario.getEmail());
			mensagem.setSubject("Condomínio App - Redefinição de Senha");
			mensagem.setText(
					"Acesse o endereço abaixo para redefinir sua senha:\n\nhttp://localhost:8080/conta/redefinir?username="
							+ username + "&token=" + usuario.getPassword().substring(8));
			emailSender.send(mensagem);
			return true;
		} else
			return false;
	}

	@Override
	public boolean redefinirSenha(String username, String token, String password) {
		// LATER Alterar redefinição de senha para tabela de tokens e expiração
		Usuario usuario = usuarioDao.findByUsername(username);
		if (usuario != null && usuario.getPassword().substring(8).equals(token)) {
			usuario.setPassword(passwordEncoder.encode(password));
			usuarioDao.save(usuario);
			return true;
		} else
			return false;
	}

}
