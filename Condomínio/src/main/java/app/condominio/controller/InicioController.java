package app.condominio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InicioController {

	@GetMapping({ "/", "" })
	public ModelAndView home(ModelMap model) {
		model.addAttribute("titulo", "Página Inicial");
		model.addAttribute("conteudo", "inicio");
		return new ModelAndView("site/layout", model);
	}

}
