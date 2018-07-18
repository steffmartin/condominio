package app.condominio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public ModelAndView getCadastroSindico(@ModelAttribute("sindico") Usuario sindico) {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "sindicoCadastrar");
	}

	@PostMapping("/cadastrar")
	public ModelAndView postCadastroSindico(@Valid @ModelAttribute("sindico") Usuario sindico, BindingResult validacao) {
		usuarioService.validar(sindico, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSite", "conteudo", "sindicoCadastrar");
		}
		usuarioService.salvarSindico(sindico);
		return new ModelAndView("redirect:/login?novo");
	}

	@GetMapping("/redefinir")
	public ModelAndView preRedefinir() {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "usuarioRedefinirSenha");
	}

	@PostMapping("/redefinir")
	public String postRedefinir(@RequestParam("username") String username) {
		if (usuarioService.redefinirSenha(username)) {
			return "redirect:/conta/redefinir?email&username=" + username;
		} else
			return "redirect:/conta/redefinir?erro&username=" + username;
	}

	@PutMapping("/redefinir")
	public String putRedefinir(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("token") String token) {
		if (usuarioService.redefinirSenha(username, token, password)) {
			return "redirect:/login?redefinido";
		} else
			return "redirect:/conta/redefinir?invalido";
	}
}
