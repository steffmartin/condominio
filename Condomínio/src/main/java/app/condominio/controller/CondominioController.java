package app.condominio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.condominio.domain.Condominio;
import app.condominio.domain.enums.Estado;
import app.condominio.service.CondominioService;

@Controller
@RequestMapping("sindico/condominio")
public class CondominioController {

	@Autowired
	private CondominioService condominioService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "condominio", "cadastro" };
	}

	@ModelAttribute("estados")
	public Estado[] estados() {
		return Estado.values();
	}

	@GetMapping("/cadastro")
	public ModelAndView getCondominioCadastro(ModelMap model) {
		Condominio condominio = condominioService.ler();
		if (condominio != null) {
			model.addAttribute("condominio", condominio);
		} else {
			model.addAttribute("condominio", new Condominio());
		}
		model.addAttribute("conteudo", "condominioCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postCondominioCadastro(@Valid @ModelAttribute("condominio") Condominio condominio,
			BindingResult validacao) {
		condominioService.validar(condominio, validacao);
		if (validacao.hasErrors()) {
			condominio.setIdCondominio(null);
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "condominioCadastro");
		}
		condominioService.salvar(condominio);
		return new ModelAndView("redirect:/sindico");
	}

	@PutMapping("/cadastro")
	public ModelAndView putCondominioCadastro(@Valid @ModelAttribute("condominio") Condominio condominio,
			BindingResult validacao) {
		condominioService.validar(condominio, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "condominioCadastro");
		}
		condominioService.editar(condominio);
		return new ModelAndView("redirect:/sindico");
	}

}
