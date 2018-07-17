package app.condominio.service;

import app.condominio.domain.Usuario;

public interface UsuarioService {

	public void salvarSindico(Usuario usuario);

	public void salvarMorador(Usuario usuario);
	
	public void salvarAdmin(Usuario usuario);

	public Usuario ler(String username);
	
	public boolean existe(String username);

	public void editar(Usuario usuario);

	public void excluir(Usuario usuario);
	
	public boolean redefinirSenha(String username);
	
	public boolean redefinirSenha(String username, String token, String password);

}
