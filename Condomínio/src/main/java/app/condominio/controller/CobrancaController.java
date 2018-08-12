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

import app.condominio.domain.Cobranca;
import app.condominio.domain.Moradia;
import app.condominio.domain.enums.MotivoBaixa;
import app.condominio.domain.enums.MotivoEmissao;
import app.condominio.domain.enums.SituacaoCobranca;
import app.condominio.service.CobrancaService;
import app.condominio.service.MoradiaService;

@Controller
@RequestMapping("sindico/cobrancas")
public class CobrancaController {

	@Autowired
	private CobrancaService cobrancaService;

	@Autowired
	MoradiaService moradiaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "financeiro", "cobrancas" };
	}

	@ModelAttribute("motivosEmissao")
	public MotivoEmissao[] motivosEmissao() {
		return MotivoEmissao.values();
	}

	@ModelAttribute("motivosBaixa")
	public MotivoBaixa[] motivosBaixa() {
		return MotivoBaixa.values();
	}

	@ModelAttribute("situacoes")
	public SituacaoCobranca[] situacoes() {
		return SituacaoCobranca.values();
	}

	@ModelAttribute("moradias")
	public List<Moradia> moradias() {
		return moradiaService.listar();
	}

	@GetMapping({ "", "/", "/lista", "/todos" })
	public ModelAndView getCobrancas(ModelMap model) {
		model.addAttribute("cobrancas", cobrancaService.listar());
		model.addAttribute("conteudo", "cobrancaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getCobrancaCadastro(@ModelAttribute("cobranca") Cobranca cobranca) {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "cobrancaCadastro");
	}

	@GetMapping("/{idCobranca}/cadastro")
	public ModelAndView getCobrancaEditar(@PathVariable("idCobranca") Long idCobranca, ModelMap model) {
		model.addAttribute("cobranca", cobrancaService.ler(idCobranca));
		model.addAttribute("conteudo", "cobrancaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postCobrancaCadastro(@Valid @ModelAttribute("cobranca") Cobranca cobranca,
			BindingResult validacao) {
		cobrancaService.validar(cobranca, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "cobrancaCadastro");
		}
		cobrancaService.salvar(cobranca);
		return new ModelAndView("redirect:/sindico/cobrancas");
	}

	@PutMapping("/cadastro")
	public ModelAndView putCobrancaCadastro(@Valid @ModelAttribute("cobranca") Cobranca cobranca,
			BindingResult validacao) {
		cobrancaService.validar(cobranca, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "cobrancaCadastro");
		}
		cobrancaService.editar(cobranca);
		return new ModelAndView("redirect:/sindico/cobrancas");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteCobrancaCadastro(@RequestParam("idObj") Long idObj) {
		cobrancaService.excluir(cobrancaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/cobrancas");
	}
}
