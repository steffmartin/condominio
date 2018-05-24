package app.condominio.dao;

import app.condominio.domain.Usuario;

public interface UsuarioDao {
	
	void salvar(Usuario usuario);
	
	void editar(Usuario usuario);
	
	void excluir(Usuario usuario);
	
	Usuario get(String username);

}
