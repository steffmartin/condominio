package app.condominio.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.condominio.domain.Usuario;

public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

	Usuario findOneByUsername(String username);

	Boolean existsByUsername(String username);

}
