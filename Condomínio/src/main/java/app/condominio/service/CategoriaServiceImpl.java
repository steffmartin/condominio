package app.condominio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.CategoriaDao;
import app.condominio.domain.Categoria;
import app.condominio.domain.Condominio;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaDao categoriaDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Categoria entidade) {
		Categoria categoriaPai = entidade.getCategoriaPai();
		if (categoriaPai != null) {
			entidade.setNivel(categoriaPai.getNivel() + 1);
			entidade.setCondominio(categoriaPai.getCondominio());
		} else {
			entidade.setNivel(1);
			entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		}
		categoriaDao.save(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Categoria ler(Long id) {
		return categoriaDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Categoria> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<Categoria>();
		}
		return condominio.getCategorias();
	}

	@Override
	public void editar(Categoria entidade) {
		Categoria categoriaPai = entidade.getCategoriaPai();
		if (categoriaPai != null) {
			entidade.setNivel(categoriaPai.getNivel() + 1);
		} else {
			entidade.setNivel(1);
		}
		categoriaDao.save(entidade);
	}

	@Override
	public void excluir(Categoria entidade) {
		categoriaDao.delete(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean haCondominio() {
		return usuarioService.lerLogado().getCondominio() != null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Categoria entidade, BindingResult validacao) {
		Categoria categoriaPai = entidade.getCategoriaPai();
		if (categoriaPai != null) {
			if (categoriaPai.getNivel() >= Categoria.NIVEL_MAX) {
				validacao.rejectValue("categoriaPai", "Max", new Object[] { 0, Categoria.NIVEL_MAX }, null);
			}
			if (categoriaPai.getTipo() != entidade.getTipo()) {
				validacao.rejectValue("tipo", "typeMismatch");
			}

		}
		if (entidade.getIdCategoria() != null) {
			Categoria anterior = ler(entidade.getIdCategoria());
			if (entidade.getTipo() != anterior.getTipo()) {
				validacao.rejectValue("tipo", "Final");
			}
		}
	}

}
