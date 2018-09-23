package app.condominio.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.condominio.domain.Bloco;
import app.condominio.service.BlocoService;

@Controller
@RequestMapping("sindico/blocos")
public class BlocoController {

	@Autowired
	private BlocoService blocoService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "condominio", "blocos" };
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getBlocos(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("blocos",
				blocoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "blocoLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getBlocoCadastro(@ModelAttribute("bloco") Bloco bloco) {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "blocoCadastro");
	}

	@GetMapping("/{idBloco}/cadastro")
	public ModelAndView getBlocoEditar(@PathVariable("idBloco") Long idBloco, ModelMap model) {
		model.addAttribute("bloco", blocoService.ler(idBloco));
		model.addAttribute("conteudo", "blocoCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postBlocoCadastro(@Valid @ModelAttribute("bloco") Bloco bloco, BindingResult validacao) {
		blocoService.validar(bloco, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "blocoCadastro");
		}
		blocoService.salvar(bloco);
		return new ModelAndView("redirect:/sindico/blocos");
	}

	@PutMapping("/cadastro")
	public ModelAndView putBlocoCadastro(@Valid @ModelAttribute("bloco") Bloco bloco, BindingResult validacao) {
		blocoService.validar(bloco, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "blocoCadastro");
		}
		blocoService.editar(bloco);
		return new ModelAndView("redirect:/sindico/blocos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteBlocoCadastro(@RequestParam("idObj") Long idObj) {
		blocoService.excluir(blocoService.ler(idObj));
		return new ModelAndView("redirect:/sindico/blocos");
	}

}
