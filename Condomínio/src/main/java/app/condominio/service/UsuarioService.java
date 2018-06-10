package app.condominio.service;

import app.condominio.domain.Usuario;

public interface UsuarioService {

	public void salvar(Usuario usuario);

	public void salvarSindico(Usuario usuario);

	public void salvarMorador(Usuario usuario);

	public Usuario ler(String username);

	public void editar(Usuario usuario);

	public void excluir(Usuario usuario);

}
