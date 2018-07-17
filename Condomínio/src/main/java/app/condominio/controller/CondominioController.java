package app.condominio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

	@ModelAttribute("estados")
	public Estado[] estados() {
		return Estado.values();
	}

	@GetMapping("/cadastrar")
	public ModelAndView preCadastroCondominio(@ModelAttribute("condominio") Condominio condominio, ModelMap model) {
		model.addAttribute("conteudo", "cadastrarCondominio");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastrar")
	public ModelAndView posCadastroCondominio(@Valid @ModelAttribute("condominio") Condominio condominio,
			BindingResult validacao, ModelMap model) {
		if (validacao.hasErrors()) {
			return preCadastroCondominio(condominio, model);
		}
		condominioService.salvar(condominio);
		return new ModelAndView("redirect:/sindico?condominio", model);
	}

}
