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
		// TODO reescrever ordem das categoriasFilhas
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
		Long idCategoria = entidade.getIdCategoria();
		// Se tiver uma categoria pai...
		if (categoriaPai != null) {
			// Não pode criar mais níveis do que o parametrizado
			if (categoriaPai.getNivel() >= Categoria.NIVEL_MAX) {
				validacao.rejectValue("categoriaPai", "Max", new Object[] { 0, Categoria.NIVEL_MAX }, null);
			}
			// Não pode ter um tipo diferente do tipo do pai
			if (categoriaPai.getTipo() != entidade.getTipo()) {
				validacao.rejectValue("categoriaPai", "typeMismatch", new Object[] { 0, "não é do mesmo tipo" }, null);
			}
			// Não pode ser filha dela mesma ou de uma das filhas dela
			// if (idCategoria != null && (categoriaPai.equals(entidade) ||
			// ehSuperior(ler(idCategoria), categoriaPai))) {
			if (idCategoria != null && (categoriaPai.equals(entidade)
					|| categoriaPai.getOrdem().startsWith(ler(idCategoria).getOrdem()))) {
				validacao.rejectValue("categoriaPai", "typeMismatch", new Object[] { 0, "é igual ou inferior a esta" },
						null);
			}
			// Ordem tem que ser sequência da categoria superior
			if (!entidade.getOrdem().startsWith(categoriaPai.getOrdem())) {
				validacao.rejectValue("ordem", "typeMismatch");
			}
		}
		if (idCategoria != null) {
			// Não pode "alterar" o tipo da categoria de RECEITA para DESPESA e vice-versa
			Categoria anterior = ler(entidade.getIdCategoria());
			if (entidade.getTipo() != anterior.getTipo()) {
				validacao.rejectValue("tipo", "Final");
			}
		}
	}

	// Diz se 1 é superior a 2 hierarquicamente (método recursivo)
	/*
	 * @Transactional(readOnly = true, propagation = Propagation.SUPPORTS) private
	 * boolean ehSuperior(Categoria categoria1, Categoria categoria2) { Boolean
	 * retorno = false; if (!categoria1.getCategoriasFilhas().isEmpty()) { for
	 * (Categoria c : categoria1.getCategoriasFilhas()) { if (c.equals(categoria2))
	 * { retorno = true; break; } else { retorno = ehSuperior(c, categoria2); } } }
	 * return retorno; }
	 */

}
