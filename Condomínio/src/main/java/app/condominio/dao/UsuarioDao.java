package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

	Usuario findByUsername(String username);

}
