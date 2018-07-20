package app.condominio.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import app.condominio.domain.Moradia;
import app.condominio.domain.enums.TipoMoradia;
import app.condominio.service.BlocoService;
import app.condominio.service.MoradiaService;

@Controller
@RequestMapping("sindico/moradias")
public class MoradiaController {

	@Autowired
	private MoradiaService moradiaService;

	@Autowired
	private BlocoService blocoService;

	@ModelAttribute("tipos")
	public TipoMoradia[] tipos() {
		return TipoMoradia.values();
	}

	@ModelAttribute("blocos")
	public List<Bloco> blocos() {
		return blocoService.listar();
	}

	@GetMapping({ "", "/", "lista", "todos" })
	public ModelAndView getMoradias(ModelMap model) {
		model.addAttribute("moradias", moradiaService.listar());
		model.addAttribute("conteudo", "moradiaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getMoradiaCadastro(ModelMap model) {
		model.addAttribute("moradia", new Moradia());
		model.addAttribute("conteudo", "moradiaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idMoradia}/cadastro")
	public ModelAndView getMoradiaEditar(@PathVariable("idMoradia") Long idMoradia, ModelMap model) {
		model.addAttribute("moradia", moradiaService.ler(idMoradia));
		model.addAttribute("conteudo", "moradiaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postMoradiaCadastro(@Valid @ModelAttribute("moradia") Moradia moradia,
			BindingResult validacao) {
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "moradiaCadastro");
		}
		moradiaService.salvar(moradia);
		return new ModelAndView("redirect:/sindico/moradias");
	}

	@PutMapping("/cadastro")
	public ModelAndView putMoradiaCadastro(@Valid @ModelAttribute("moradia") Moradia moradia, BindingResult validacao) {
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "moradiaCadastro");
		}
		moradiaService.editar(moradia);
		return new ModelAndView("redirect:/sindico/moradias");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteMoradiaCadastro(@RequestParam("idObj") Long idObj) {
		moradiaService.excluir(moradiaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/moradias");
	}
}
