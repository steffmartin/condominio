package app.condominio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import app.condominio.domain.Usuario;
import app.condominio.domain.enums.Estado;
import app.condominio.service.CondominioService;
import app.condominio.service.UsuarioService;

@Controller
@RequestMapping("sindico/condominio")
public class CondominioController {

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private UsuarioService usuarioService;

	@ModelAttribute("estados")
	public Estado[] estados() {
		return Estado.values();
	}

	@GetMapping("/cadastro")
	public ModelAndView getCadastroCondominio(ModelMap model, Authentication authentication) {
		Condominio condominio = usuarioService.ler(authentication.getName()).getCondominio();
		if (condominio != null) {
			model.addAttribute("condominio", condominio);
		} else {
			model.addAttribute("condominio", new Condominio());
		}
		model.addAttribute("conteudo", "cadastroCondominio");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postCadastroCondominio(@Valid @ModelAttribute("condominio") Condominio condominio,
			BindingResult validacao, Authentication authentication) {
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "cadastroCondominio");
		}
		condominioService.salvar(condominio);

		// TODO alterar esta chamada quando criar meu UserDetais
		Usuario sindico = usuarioService.ler(authentication.getName());
		sindico.setCondominio(condominio);
		usuarioService.editar(sindico);
		return new ModelAndView("redirect:/sindico");
	}

	@PutMapping("/cadastro")
	public ModelAndView putCadastroCondominio(@Valid @ModelAttribute("condominio") Condominio condominio,
			BindingResult validacao, Authentication authentication) {

		// Adiciona o ID do condomínio
		condominio.setIdCondominio(usuarioService.ler(authentication.getName()).getCondominio().getIdCondominio());

		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "cadastroCondominio");
		}
		condominioService.editar(condominio);
		return new ModelAndView("redirect:/sindico");
	}

}
