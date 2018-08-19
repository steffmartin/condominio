package app.condominio.service;

import org.springframework.validation.BindingResult;

import app.condominio.domain.Usuario;

public interface UsuarioService {

	public void salvarSindico(Usuario usuario);

	public void salvarCondomino(Usuario usuario);

	public void salvarAdmin(Usuario usuario);

	public Usuario ler(Long id);

	public Usuario ler(String username);

	public Usuario lerLogado();

	public void editar(Usuario usuario);

	public void excluir(Usuario usuario);

	public boolean redefinirSenha(String username);

	public boolean redefinirSenha(String username, String token, String password);

	public void validar(Usuario usuario, BindingResult validacao);

}
