package app.condominio.service;

import app.condominio.domain.Usuario;

public interface UsuarioService extends CrudService<Usuario, Long> {

	public void salvarSindico(Usuario usuario);

	public void salvarCondomino(Usuario usuario);

	public void salvarAdmin(Usuario usuario);

	public Usuario ler(String username);

	public Usuario lerLogado();

	public boolean redefinirSenha(String username);

	public boolean redefinirSenha(String username, String token, String password);

}
