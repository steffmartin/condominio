package app.condominio.controller;

import java.time.LocalDate;
import java.util.List;
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

import app.condominio.domain.Categoria;
import app.condominio.domain.Conta;
import app.condominio.domain.Lancamento;
import app.condominio.domain.Movimento;
import app.condominio.domain.Transferencia;
import app.condominio.domain.enums.TipoCategoria;
import app.condominio.domain.enums.TipoClasseMovimento;
import app.condominio.service.CategoriaService;
import app.condominio.service.ContaService;
import app.condominio.service.MovimentoService;

@Controller
@RequestMapping({ "sindico/movimentos", "sindico/lancamentos" })
public class MovimentoController {

	@Autowired
	private MovimentoService movimentoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ContaService contaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "contabilidade", "movimentos" };
	}

	@ModelAttribute("tiposMovimento")
	public TipoCategoria[] tiposMovimento() {
		return TipoCategoria.values();
	}

	@ModelAttribute("tiposClasseMovimento")
	public TipoClasseMovimento[] tiposClasseMovimento() {
		return TipoClasseMovimento.values();
	}

	@ModelAttribute("categorias")
	public List<Categoria> categorias() {
		return categoriaService.listar();
	}

	@ModelAttribute("contas")
	public List<Conta> contas() {
		return contaService.listar();
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getMovimentos(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("movimentos",
				movimentoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "movimentoLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getMovimentoCadastro(@ModelAttribute("movimento") Movimento movimento) {
		movimento.setData(LocalDate.now());
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "movimentoCadastro");
	}

	@GetMapping("/{idMovimento}/cadastro")
	public ModelAndView getMovimentoEditar(@PathVariable("idMovimento") Long idMovimento, ModelMap model) {
		Movimento movimento = movimentoService.ler(idMovimento);
		if (movimento instanceof Lancamento) {
			model.addAttribute("movimento", movimento);
			model.addAttribute("tipo", TipoClasseMovimento.L);
		} else {
			model.addAttribute("movimento", movimento);
			model.addAttribute("tipo", TipoClasseMovimento.T);
		}
		model.addAttribute("conteudo", "movimentoCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping(value = "/cadastro", params = { "L" })
	public ModelAndView postMovimentoLancamentoCadastro(@Valid @ModelAttribute("movimento") Lancamento movimento,
			BindingResult validacao, ModelMap model) {
		movimentoService.validar(movimento, validacao);
		if (validacao.hasErrors()) {
			movimento.setIdMovimento(null);
			model.addAttribute("tipo", TipoClasseMovimento.L);
			model.addAttribute("conteudo", "movimentoCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		movimentoService.salvar(movimento);
		return new ModelAndView("redirect:/sindico/movimentos");
	}

	@PostMapping(value = "/cadastro", params = { "T" })
	public ModelAndView postMovimentoTransferenciaCadastro(@Valid @ModelAttribute("movimento") Transferencia movimento,
			BindingResult validacao, ModelMap model) {
		movimentoService.validar(movimento, validacao);
		if (validacao.hasErrors()) {
			movimento.setIdMovimento(null);
			model.addAttribute("tipo", TipoClasseMovimento.T);
			model.addAttribute("conteudo", "movimentoCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		movimentoService.salvar(movimento);
		return new ModelAndView("redirect:/sindico/movimentos");
	}

	@PutMapping(value = "/cadastro", params = { "L" })
	public ModelAndView putMovimentoLancamentoCadastro(@Valid @ModelAttribute("movimento") Lancamento movimento,
			BindingResult validacao, ModelMap model) {
		movimentoService.validar(movimento, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoClasseMovimento.L);
			model.addAttribute("conteudo", "movimentoCadastro");
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "movimentoCadastro");
		}
		movimentoService.editar(movimento);
		return new ModelAndView("redirect:/sindico/movimentos");
	}

	@PutMapping(value = "/cadastro", params = { "T" })
	public ModelAndView putMovimentoTransferenciaCadastro(@Valid @ModelAttribute("movimento") Transferencia movimento,
			BindingResult validacao, ModelMap model) {
		movimentoService.validar(movimento, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoClasseMovimento.T);
			model.addAttribute("conteudo", "movimentoCadastro");
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "movimentoCadastro");
		}
		movimentoService.editar(movimento);
		return new ModelAndView("redirect:/sindico/movimentos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteMovimentoCadastro(@RequestParam("idObj") Long idObj) {
		movimentoService.excluir(movimentoService.ler(idObj));
		return new ModelAndView("redirect:/sindico/movimentos");
	}

}
