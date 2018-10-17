package app.condominio.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;
import app.condominio.service.CategoriaService;
import app.condominio.service.OrcamentoService;
import app.condominio.service.PeriodoService;

@Controller
@RequestMapping("sindico/orcamentos")
public class OrcamentoController {

	@Autowired
	private OrcamentoService orcamentoService;

	@Autowired
	private PeriodoService periodoService;

	@Autowired
	private CategoriaService categoriaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "contabilidade", "orcamentos" };
	}

	@ModelAttribute("periodos")
	public List<Periodo> periodos() {
		return periodoService.listar();
	}

	@ModelAttribute("categorias")
	public List<Categoria> categorias() {
		return categoriaService.listar();
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getOrcamentos(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("orcamentos",
				orcamentoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "orcamentoLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getOrcamentoCadastro(@ModelAttribute("orcamento") Orcamento orcamento) {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "orcamentoCadastro");
	}

	@GetMapping("/{idOrcamento}/cadastro")
	public ModelAndView getOrcamentoEditar(@PathVariable("idOrcamento") Long idOrcamento, ModelMap model) {
		model.addAttribute("orcamento", orcamentoService.ler(idOrcamento));
		model.addAttribute("conteudo", "orcamentoCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postOrcamentoCadastro(@Valid @ModelAttribute("orcamento") Orcamento orcamento,
			BindingResult validacao) {
		orcamentoService.validar(orcamento, validacao);
		if (validacao.hasErrors()) {
			orcamento.setIdOrcamento(null);
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "orcamentoCadastro");
		}
		orcamentoService.salvar(orcamento);
		return new ModelAndView("redirect:/sindico/orcamentos");
	}

	@PutMapping("/cadastro")
	public ModelAndView putOrcamentoCadastro(@Valid @ModelAttribute("orcamento") Orcamento orcamento,
			BindingResult validacao) {
		orcamentoService.validar(orcamento, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "orcamentoCadastro");
		}
		orcamentoService.editar(orcamento);
		return new ModelAndView("redirect:/sindico/orcamentos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteOrcamentoCadastro(@RequestParam("idObj") Long idObj) {
		orcamentoService.excluir(orcamentoService.ler(idObj));
		return new ModelAndView("redirect:/sindico/orcamentos");
	}

}
