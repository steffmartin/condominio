package app.condominio.service;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.dao.UsuarioDao;
import app.condominio.domain.Usuario;
import app.condominio.domain.enums.Autorizacao;

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
		salvar(usuario);
	}

	@Override
	public void salvarMorador(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.MORADOR);
		salvar(usuario);
	}
	
	@Override
	public void salvarAdmin(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.ADMIN);
		salvar(usuario);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean existe(String username) {
		return username != null && usuarioDao.existsByUsername(username);
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
							+ username + "&token=" + getToken(usuario.getPassword())
							+ "\n\nCaso não consiga clicar no link acima, copie-o e cole em seu navegador."
							+ "\n\nPor segurança este link só é válido até o final do dia.");
			emailSender.send(mensagem);
			return true;
		} else
			return false;
	}

	@Override
	public boolean redefinirSenha(String username, String token, String password) {
		// LATER Alterar redefinição de senha para tabela de tokens e expiração
		Usuario usuario = usuarioDao.findByUsername(username);
		if (usuario != null && getToken(usuario.getPassword()).equals(token)) {
			usuario.setPassword(passwordEncoder.encode(password));
			usuarioDao.save(usuario);
			return true;
		} else
			return false;
	}

	private String getToken(String texto) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		String d = "" + calendar.get(Calendar.DAY_OF_YEAR);
		String a = "" + (calendar.get(Calendar.YEAR) - 2000);
		String regex = "\\\\|/|\\?|\\.|&|\\$"; // Regex in java: http://www.regexplanet.com/advanced/java/index.html

		return texto.substring(8).replaceAll(regex, d) + a;
	}
}