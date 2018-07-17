package app.condominio.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InicioController {

	@GetMapping({ "/", "", "/home", "/inicio" })
	public ModelAndView inicio(ModelMap model) {
		model.addAttribute("conteudo", "inicio");
		return new ModelAndView("fragmentos/layoutSite", model);
	}

	@GetMapping({ "/entrar", "/login" })
	public ModelAndView preLogin(ModelMap model) {
		model.addAttribute("conteudo", "login");
		return new ModelAndView("fragmentos/layoutSite", model);
	}

	@GetMapping("/autenticado")
	public String posLogin(Authentication authentication) {
		String retorno = "redirect:/login?erro";
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("SINDICO"))) {
			retorno = "redirect:/sindico";
		} else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("MORADOR"))) {
			retorno = "redirect:/morador";
		} else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			retorno = "redirect:/admin";
		}
		return retorno;
	}
	
	@GetMapping("/sindico")
	public ModelAndView sindico(ModelMap model) {
		model.addAttribute("conteudo", "inicio");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}
}
