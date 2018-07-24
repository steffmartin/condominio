package app.condominio.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.condominio.domain.Categoria;
import app.condominio.domain.Subcategoria;
import app.condominio.domain.enums.TipoCategoria;
import app.condominio.domain.enums.TipoClasseCategoria;
import app.condominio.service.CategoriaService;
import app.condominio.service.SubcategoriaService;

@Controller
@RequestMapping("sindico/subcategorias")
public class SubcategoriaController {

	@Autowired
	private SubcategoriaService subcategoriaService;

	@Autowired
	private CategoriaService categoriaService;

	@ModelAttribute("tiposCategoria")
	public TipoCategoria[] tiposCategoria() {
		return TipoCategoria.values();
	}

	@ModelAttribute("tiposClasseCategoria")
	public TipoClasseCategoria[] tiposClasseCategoria() {
		return TipoClasseCategoria.values();
	}

	@ModelAttribute("haCondominio")
	public boolean haCondominio() {
		return categoriaService.haCondominio();
	}

	@ModelAttribute("categorias")
	public List<Categoria> categorias() {
		return categoriaService.listar();
	}

	@GetMapping({ "", "/", "/lista", "/todos" })
	public ModelAndView getSubcategorias() {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "categoriaLista");
	}

	@GetMapping("/cadastro")
	public ModelAndView getSubcategoriaCadastro(ModelMap model) {
		model.addAttribute("classe", TipoClasseCategoria.S);
		model.addAttribute("categoria", new Subcategoria());
		model.addAttribute("conteudo", "categoriaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idSubcategoria}/cadastro")
	public ModelAndView getSubcategoriaEditar(@PathVariable("idSubcategoria") Long idSubcategoria, ModelMap model) {
		model.addAttribute("classe", TipoClasseCategoria.S);
		model.addAttribute("categoria", subcategoriaService.ler(idSubcategoria));
		model.addAttribute("conteudo", "categoriaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postSubcategoriaCadastro(@Valid @ModelAttribute("categoria") Subcategoria categoria,
			BindingResult validacao, ModelMap model) {
		subcategoriaService.validar(categoria, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("classe", TipoClasseCategoria.S);
			model.addAttribute("conteudo", "categoriaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		subcategoriaService.salvar(categoria);
		return new ModelAndView("redirect:/sindico/subcategorias");
	}

	@PutMapping("/cadastro")
	public ModelAndView putSubcategoriaCadastro(@Valid @ModelAttribute("categoria") Subcategoria categoria,
			BindingResult validacao, ModelMap model) {
		subcategoriaService.validar(categoria, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("classe", TipoClasseCategoria.S);
			model.addAttribute("conteudo", "categoriaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		subcategoriaService.editar(categoria);
		return new ModelAndView("redirect:/sindico/subcategorias");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteSubcategoriaCadastro(@RequestParam("idObj") Long idObj) {
		subcategoriaService.excluir(subcategoriaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/subcategorias");
	}
}
