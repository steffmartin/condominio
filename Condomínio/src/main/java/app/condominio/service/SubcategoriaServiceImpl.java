package app.condominio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.SubcategoriaDao;
import app.condominio.domain.Subcategoria;

@Service
@Transactional
public class SubcategoriaServiceImpl implements SubcategoriaService {

	@Autowired
	private SubcategoriaDao subcategoriaDao;

	@Autowired
	private CategoriaService categoriaService;

	@Override
	public void salvar(Subcategoria entidade) {
		subcategoriaDao.save(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Subcategoria ler(Long id) {
		return subcategoriaDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subcategoria> listar() {
		return subcategoriaDao.findAllByCategoria(categoriaService.listar());
	}

	@Override
	public void editar(Subcategoria entidade) {
		subcategoriaDao.save(entidade);

	}

	@Override
	public void excluir(Subcategoria entidade) {
		subcategoriaDao.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Subcategoria entidade, BindingResult validacao) {
		// TODO Auto-generated method stub

	}

}
