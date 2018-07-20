package app.condominio.controller;

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

import app.condominio.domain.Pessoa;
import app.condominio.domain.PessoaFisica;
import app.condominio.domain.PessoaJuridica;
import app.condominio.domain.enums.Genero;
import app.condominio.domain.enums.TipoPessoa;
import app.condominio.service.PessoaService;

@Controller
@RequestMapping({ "sindico/pessoas", "sindico/condominos" })
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@ModelAttribute("generos")
	public Genero[] generos() {
		return Genero.values();
	}

	@ModelAttribute("tipos")
	public TipoPessoa[] tiposPessoa() {
		return TipoPessoa.values();
	}

	@ModelAttribute("haCondominio")
	public boolean haCondominio() {
		return pessoaService.haCondominio();
	}

	@GetMapping({ "", "/", "lista", "todos" })
	public ModelAndView getPessoas(ModelMap model) {
		model.addAttribute("pessoas", pessoaService.listar());
		model.addAttribute("conteudo", "pessoaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getPessoaCadastro(ModelMap model) {
		model.addAttribute("pessoa", new Pessoa());
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "pessoaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idPessoa}/cadastro-pf")
	public ModelAndView getPessoaFisicaEditar(@PathVariable("idPessoa") Long idPessoa, ModelMap model) {
		model.addAttribute("pessoa", (PessoaFisica) pessoaService.ler(idPessoa));
		model.addAttribute("tipo", TipoPessoa.F);
		model.addAttribute("conteudo", "pessoaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idPessoa}/cadastro-pj")
	public ModelAndView getPessoaJuridicaEditar(@PathVariable("idPessoa") Long idPessoa, ModelMap model) {
		model.addAttribute("pessoa", (PessoaJuridica) pessoaService.ler(idPessoa));
		model.addAttribute("tipo", TipoPessoa.J);
		model.addAttribute("conteudo", "pessoaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro-pf")
	public ModelAndView postPessoaFisicaCadastro(@Valid @ModelAttribute("pessoa") PessoaFisica pessoa,
			BindingResult validacao, ModelMap model) {
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoPessoa.F);
			model.addAttribute("conteudo", "pessoaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		pessoaService.salvar(pessoa);
		return new ModelAndView("redirect:/sindico/condominos");
	}

	@PostMapping("/cadastro-pj")
	public ModelAndView postPessoaJuridicaCadastro(@Valid @ModelAttribute("pessoa") PessoaJuridica pessoa,
			BindingResult validacao, ModelMap model) {
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoPessoa.J);
			model.addAttribute("conteudo", "pessoaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		pessoaService.salvar(pessoa);
		return new ModelAndView("redirect:/sindico/condominos");
	}

	@PutMapping("/cadastro-pf")
	public ModelAndView putPessoaFisicaCadastro(@Valid @ModelAttribute("pessoa") PessoaFisica pessoa,
			BindingResult validacao, ModelMap model) {
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoPessoa.F);
			model.addAttribute("conteudo", "pessoaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		pessoaService.editar(pessoa);
		return new ModelAndView("redirect:/sindico/condominos");
	}

	@PutMapping("/cadastro-pj")
	public ModelAndView putPessoaJuridicaCadastro(@Valid @ModelAttribute("pessoa") PessoaJuridica pessoa,
			BindingResult validacao, ModelMap model) {
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoPessoa.J);
			model.addAttribute("conteudo", "pessoaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		pessoaService.editar(pessoa);
		return new ModelAndView("redirect:/sindico/condominos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deletePessoaCadastro(@RequestParam("idObj") Long idObj) {
		pessoaService.excluir(pessoaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/condominos");
	}

}
