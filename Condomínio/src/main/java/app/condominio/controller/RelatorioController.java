package app.condominio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sindico/relatorios")
public class RelatorioController {

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "relatorios", "" };
	}

	@GetMapping({ "/", "", "/home", "/inicio" })
	public ModelAndView inicio() {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "relatorioLista");
	}

	@GetMapping("/livroCaixa")
	public ModelAndView livroCaixa() {
		return new ModelAndView("fragmentos/layoutRelatorio", "conteudo", "relatorioLivroCaixa");
	}

}
