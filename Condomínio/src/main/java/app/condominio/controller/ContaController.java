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

import app.condominio.domain.Conta;
import app.condominio.domain.ContaBancaria;
import app.condominio.domain.enums.TipoConta;
import app.condominio.domain.enums.TipoContaBancaria;
import app.condominio.service.ContaService;

@Controller
@RequestMapping("sindico/contas")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@ModelAttribute("tipos")
	public TipoConta[] tiposConta() {
		return TipoConta.values();
	}

	@ModelAttribute("tiposBc")
	public TipoContaBancaria[] tiposContaBancaria() {
		return TipoContaBancaria.values();
	}

	@ModelAttribute("haCondominio")
	public boolean haCondominio() {
		return contaService.haCondominio();
	}

	@GetMapping({ "", "/", "/lista", "/todos" })
	public ModelAndView getContas(ModelMap model) {
		model.addAttribute("contas", contaService.listar());
		model.addAttribute("conteudo", "contaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getContaCadastro(ModelMap model) {
		model.addAttribute("conta", new Conta());
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "contaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idConta}/cadastro")
	public ModelAndView getContaEditar(@PathVariable("idConta") Long idConta, ModelMap model) {
		Conta conta = contaService.ler(idConta);
		if (conta instanceof ContaBancaria) {
			model.addAttribute("conta", (ContaBancaria) conta);
			model.addAttribute("tipo", TipoConta.BC);
		} else {
			model.addAttribute("conta", conta);
			model.addAttribute("tipo", TipoConta.CX);
		}
		model.addAttribute("conteudo", "contaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping({ "/cadastro/CX", "/cadastro" })
	public ModelAndView postContaCadastro(@Valid @ModelAttribute("conta") Conta conta, BindingResult validacao,
			ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoConta.CX);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.salvar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@PostMapping("/cadastro/BC")
	public ModelAndView postContaBancariaCadastro(@Valid @ModelAttribute("conta") ContaBancaria conta,
			BindingResult validacao, ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoConta.BC);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.salvar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@PutMapping({ "/cadastro/CX", "/cadastro" })
	public ModelAndView putContaCadastro(@Valid @ModelAttribute("conta") Conta conta, BindingResult validacao,
			ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoConta.CX);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.editar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@PutMapping("/cadastro/BC")
	public ModelAndView putContaBancariaCadastro(@Valid @ModelAttribute("conta") ContaBancaria conta,
			BindingResult validacao, ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoConta.BC);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.editar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteContaCadastro(@RequestParam("idObj") Long idObj) {
		contaService.excluir(contaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/contas");
	}

}
