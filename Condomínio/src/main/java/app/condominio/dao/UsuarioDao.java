package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

	Usuario findOneByUsername(String username);

	Boolean existsByUsername(String username);

}
