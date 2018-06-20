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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.condominio.domain.Usuario;
import app.condominio.service.UsuarioService;

@Controller
@RequestMapping("conta")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/cadastrar")
	public ModelAndView preCadastroSindico(@ModelAttribute("usuario") Usuario usuario, ModelMap model) {
		model.addAttribute("conteudo", "cadastrarSindico");
		return new ModelAndView("fragmentos/layoutSite", model);
	}

	@PostMapping("/cadastrar")
	public ModelAndView posCadastroSindico(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult validacao,
			ModelMap model) {
		if (usuarioService.existe(usuario.getUsername())) {
			validacao.rejectValue("username", "Unique");
		}
		if (validacao.hasErrors()) {
			return preCadastroSindico(usuario, model);
		}
		usuarioService.salvarSindico(usuario);
		return new ModelAndView("redirect:/login?novo", model);
	}

	@GetMapping("/redefinir")
	public ModelAndView preRedefinir(ModelMap model) {
		model.addAttribute("conteudo", "redefinir");
		return new ModelAndView("fragmentos/layoutSite", model);
	}

	@PostMapping("/redefinir")
	public String posRedefinir(@RequestParam("username") String username) {
		if (usuarioService.redefinirSenha(username)) {
			return "redirect:/conta/redefinir?email&username=" + username;
		} else
			return "redirect:/conta/redefinir?erro&username=" + username;
	}

	@PutMapping("/redefinir")
	public String fimRedefinir(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("token") String token) {
		if (usuarioService.redefinirSenha(username, token, password)) {
			return "redirect:/login?redefinido";
		} else
			return "redirect:/conta/redefinir?invalido";
	}
}
