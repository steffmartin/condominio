package app.condominio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public ModelAndView getCadastrarSindico(@ModelAttribute("sindico") Usuario sindico) {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "sindicoCadastro");
	}

	@PostMapping("/cadastrar")
	public ModelAndView postCadastrarSindico(@Valid @ModelAttribute("sindico") Usuario sindico,
			BindingResult validacao) {
		usuarioService.validar(sindico, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSite", "conteudo", "sindicoCadastro");
		}
		usuarioService.salvarSindico(sindico);
		return new ModelAndView("redirect:/login?novo");
	}

	@GetMapping("/cadastro")
	public ModelAndView getCadastroSindico(ModelMap model) {
		model.addAttribute("sindico", usuarioService.lerLogado());
		model.addAttribute("conteudo", "sindicoCadastro");
		return new ModelAndView("fragmentos/layoutSite", model);
	}

	@PutMapping("/cadastro")
	public ModelAndView putCadastroSindico(@Valid @ModelAttribute("sindico") Usuario sindico, BindingResult validacao) {
		usuarioService.validar(sindico, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSite", "conteudo", "sindicoCadastro");
		}
		usuarioService.editar(sindico);
		SecurityContextHolder.clearContext();
		return new ModelAndView("redirect:/entrar?alterado");
	}

	@GetMapping("/redefinir")
	public ModelAndView preRedefinir() {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "usuarioRedefinirSenha");
	}

	@PostMapping("/redefinir")
	public String postRedefinir(@RequestParam("username") String username) {
		if (usuarioService.redefinirSenha(username)) {
			return "redirect:/conta/redefinir?email&username=" + username;
		} else {
			return "redirect:/conta/redefinir?erro&username=" + username;
		}
	}

	@PutMapping("/redefinir")
	public String putRedefinir(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("token") String token) {
		if (usuarioService.redefinirSenha(username, token, password)) {
			return "redirect:/login?redefinido";
		} else {
			return "redirect:/conta/redefinir?invalido";
		}
	}
}
