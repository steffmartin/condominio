package app.condominio.dao;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.domain.Usuario;

@Repository
@Transactional // Default: readOnly = false, propagation = Propagation.REQUIRED
public class UsuarioDaoImpl implements UsuarioDao {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private PasswordEncoder passwordEncoder; // service

	private Session getSession() {
		return entityManagerFactory.unwrap(SessionFactory.class).openSession();
	}

	@Override
	public void create(Usuario usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // service
		getSession().save(usuario);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Usuario read(String username) {
		return getSession().bySimpleNaturalId(Usuario.class).load(username);
	}

	@Override
	public void update(Usuario usuario) {
		getSession().update(usuario);
	}

	@Override
	public void delete(Usuario usuario) {
		getSession().delete(usuario);
	}

}
