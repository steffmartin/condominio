package app.condominio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InicioController {

	@GetMapping({ "/", "", "/home", "/inicio" })
	public ModelAndView inicio(ModelMap model) {
		model.addAttribute("conteudo", "inicio");
		return new ModelAndView("site/layout", model);
	}

	@GetMapping({ "/entrar", "/login" })
	public ModelAndView preLogin(ModelMap model) {
		model.addAttribute("conteudo", "login");
		return new ModelAndView("site/layout", model);
	}

}
