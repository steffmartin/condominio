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

import app.condominio.domain.Periodo;
import app.condominio.service.PeriodoService;

@Controller
@RequestMapping("sindico/periodos")
public class PeriodoController {

	@Autowired
	private PeriodoService periodoService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "contabilidade", "periodos" };
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getPeriodos(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("periodos",
				periodoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "periodoLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getPeriodoCadastro(@ModelAttribute("periodo") Periodo periodo) {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "periodoCadastro");
	}

	@GetMapping("/{idPeriodo}/cadastro")
	public ModelAndView getPeriodoEditar(@PathVariable("idPeriodo") Long idPeriodo, ModelMap model) {
		model.addAttribute("periodo", periodoService.ler(idPeriodo));
		model.addAttribute("conteudo", "periodoCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postPeriodoCadastro(@Valid @ModelAttribute("periodo") Periodo periodo,
			BindingResult validacao) {
		periodoService.validar(periodo, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "periodoCadastro");
		}
		periodoService.salvar(periodo);
		return new ModelAndView("redirect:/sindico/periodos");
	}

	@PutMapping("/cadastro")
	public ModelAndView putPeriodoCadastro(@Valid @ModelAttribute("periodo") Periodo periodo, BindingResult validacao) {
		periodoService.validar(periodo, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "periodoCadastro");
		}
		periodoService.editar(periodo);
		return new ModelAndView("redirect:/sindico/periodos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deletePeriodoCadastro(@RequestParam("idObj") Long idObj) {
		periodoService.excluir(periodoService.ler(idObj));
		return new ModelAndView("redirect:/sindico/periodos");
	}

}
