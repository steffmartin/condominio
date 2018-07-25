package app.condominio.dao;

import org.springframework.data.repository.CrudRepository;

import app.condominio.domain.Categoria;

public interface CategoriaDao extends CrudRepository<Categoria, Long> {

	/*
	 * @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	 * 
	 * @Query("from Categoria where nivel=:nivel and idcondominio=:condominio order by ordem desc"
	 * ) Categoria findByCondominioIdByNivel(@Param("condominio") Long
	 * condominioId, @Param("nivel") int nivel);
	 */
}
