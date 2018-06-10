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

import app.condominio.domain.Usuario;
import app.condominio.service.UsuarioService;

@Controller
@RequestMapping("conta")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/cadastrar")
	public ModelAndView preCadastro(@ModelAttribute("usuario") Usuario usuario, ModelMap model) {
		model.addAttribute("conteudo", "cadastrar");
		return new ModelAndView("site/layout", model);
	}

	@PostMapping("/cadastrar")
	public ModelAndView posCadastro(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult validacao,
			ModelMap model) {
		if (validacao.hasErrors()) {
			model.addAttribute("conteudo", "cadastrar");
			return new ModelAndView("site/layout", model);
		}
		usuarioService.salvarSindico(usuario);
		model.addAttribute("conteudo", "cadastrar?condominio");
		return new ModelAndView("site/layout", model);
	}
}
