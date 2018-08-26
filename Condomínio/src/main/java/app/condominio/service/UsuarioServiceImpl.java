package app.condominio.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.UsuarioDao;
import app.condominio.domain.Usuario;
import app.condominio.domain.enums.Autorizacao;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private EmailService emailService;

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
		return usuarioDao.findOneByUsername(username);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Usuario ler(Long id) {
		return usuarioDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Usuario lerLogado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			return null;
		}
		return usuarioDao.findOneByUsername(auth.getName());
		// http://www.baeldung.com/get-user-in-spring-security
		// http://www.baeldung.com/spring-security-thymeleaf
	}

	@Override
	public void editar(Usuario usuario) {
		if (!usuario.getPassword().startsWith("{bcrypt}")) {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		}
		if (usuario.getAutorizacoes().isEmpty()) {
			usuario.setAutorizacoes(ler(usuario.getId()).getAutorizacoes());
		}
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
	public void salvarCondomino(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.CONDOMINO);
		salvar(usuario);
	}

	@Override
	public void salvarAdmin(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.ADMIN);
		salvar(usuario);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean redefinirSenha(String username) {
		Usuario usuario = ler(username);
		if (usuario != null) {
			String para = usuario.getEmail();
			String assunto = "Condomínio App - Redefinição de Senha";
			String mensagem = "Acesse o endereço abaixo para redefinir sua senha:\n\nhttp://localhost:8080/conta/redefinir?username="
					+ usuario.getUsername() + "&token=" + getToken(usuario.getPassword())
					+ "\n\nCaso não consiga clicar no link acima, copie-o e cole em seu navegador."
					+ "\n\nPor segurança este link só é válido até o final do dia.";
			emailService.enviarEmail(para, assunto, mensagem);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean redefinirSenha(String username, String token, String password) {
		// LATER Alterar redefinição de senha para tabela de tokens e expiração
		Usuario usuario = usuarioDao.findOneByUsername(username);
		if (usuario != null && getToken(usuario.getPassword()).equals(token)) {
			usuario.setPassword(password);
			editar(usuario);
			return true;
		} else {
			return false;
		}
	}

	private String getToken(String texto) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		String d = "" + calendar.get(Calendar.DAY_OF_YEAR);
		String a = "" + (calendar.get(Calendar.YEAR) - 2000);
		String regex = "\\\\|/|\\?|\\.|&|\\$"; // Regex in java: http://www.regexplanet.com/advanced/java/index.html

		return texto.substring(8).replaceAll(regex, d) + a;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Usuario usuario, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (usuario.getId() == null) {
			// Não pode criar um usuário com username repetido
			if (usuario.getUsername() != null && usuarioDao.existsByUsername(usuario.getUsername())) {
				validacao.rejectValue("username", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// Não pode criar um usuário com username repetido
			if (usuario.getUsername() != null
					&& usuarioDao.existsByUsernameAndIdNot(usuario.getUsername(), usuario.getId())) {
				validacao.rejectValue("username", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
		// Deve aceitar os termos
		if (!usuario.getAtivo()) {
			validacao.rejectValue("ativo", "AssertTrue");
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Usuario> listar() {
		// LATER fazer este método quando implementar o ADMIN
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Usuario entidade) {
		// Por enquanto nada a padronizar

	}

}