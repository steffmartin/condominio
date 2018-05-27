package app.condominio.dao;

import app.condominio.domain.Usuario;

public interface UsuarioDao {
	
	void create(Usuario usuario);
	
	Usuario read(String username);
	
	void update(Usuario usuario);
	
	void delete(Usuario usuario);
	
}
