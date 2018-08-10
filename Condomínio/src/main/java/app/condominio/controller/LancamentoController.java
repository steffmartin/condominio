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
import app.condominio.domain.Conta;
import app.condominio.domain.Movimento;
import app.condominio.service.CategoriaService;
import app.condominio.service.ContaService;
import app.condominio.service.MovimentoService;

@Controller
@RequestMapping("sindico/lancamentos")
public class LancamentoController {

	@Autowired
	private MovimentoService lancamentoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ContaService contaService;

	@ModelAttribute("categorias")
	public List<Categoria> categorias() {
		return categoriaService.listar();
	}

	@ModelAttribute("contas")
	public List<Conta> contas() {
		return contaService.listar();
	}

	@GetMapping({ "", "/", "/lista", "/todos" })
	public ModelAndView getLancamentos(ModelMap model) {
		model.addAttribute("lancamentos", lancamentoService.listar());
		model.addAttribute("conteudo", "lancamentoLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getLancamentoCadastro(@ModelAttribute("lancamento") Movimento lancamento) {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "lancamentoCadastro");
	}

	@GetMapping("/{idLancamento}/cadastro")
	public ModelAndView getLancamentoEditar(@PathVariable("idLancamento") Long idLancamento, ModelMap model) {
		model.addAttribute("lancamento", lancamentoService.ler(idLancamento));
		model.addAttribute("conteudo", "lancamentoCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postLancamentoCadastro(@Valid @ModelAttribute("lancamento") Movimento lancamento,
			BindingResult validacao) {
		lancamentoService.validar(lancamento, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "lancamentoCadastro");
		}
		lancamentoService.salvar(lancamento);
		return new ModelAndView("redirect:/sindico/lancamentos");
	}

	@PutMapping("/cadastro")
	public ModelAndView putLancamentoCadastro(@Valid @ModelAttribute("lancamento") Movimento lancamento,
			BindingResult validacao) {
		lancamentoService.validar(lancamento, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "lancamentoCadastro");
		}
		lancamentoService.editar(lancamento);
		return new ModelAndView("redirect:/sindico/lancamentos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteLancamentoCadastro(@RequestParam("idObj") Long idObj) {
		lancamentoService.excluir(lancamentoService.ler(idObj));
		return new ModelAndView("redirect:/sindico/lancamentos");
	}

}
