package app.condominio.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.condominio.domain.Movimento;
import app.condominio.service.CondominioService;
import app.condominio.service.MovimentoService;
import app.condominio.service.RelatorioService;

@Controller
@RequestMapping("sindico/relatorios")
public class RelatorioController {

	@Autowired
	RelatorioService relatorioService;

	@Autowired
	CondominioService condominioService;

	@Autowired
	MovimentoService movimentoService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "relatorios", "" };
	}

	@GetMapping({ "/", "", "/home", "/inicio" })
	public ModelAndView inicio() {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "relatorioLista");
	}

	@GetMapping("/livroCaixa")
	public ModelAndView getLivroCaixa() {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "relatorioLivroCaixa");
	}

	@PostMapping("/livroCaixa")
	public ModelAndView postLivroCaixa(
			@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim, ModelMap model) {
		if (inicio.isAfter(fim)) {
			model.addAttribute("fimInvalido", "true");
			model.addAttribute("conteudo", "relatorioLivroCaixa");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		model.addAttribute("condominio", condominioService.ler());
		model.addAttribute("inicio", inicio);
		model.addAttribute("fim", fim);
		// FIXME criar metodo pra pegar o saldo inicial
		BigDecimal saldoInicial = new BigDecimal(50).setScale(2);
		model.addAttribute("saldoInicial", saldoInicial);
		List<Movimento> lancamentos = movimentoService.listarLancamentosEntre(inicio, fim);
		model.addAttribute("lancamentos", lancamentos);
		model.addAttribute("saldos", relatorioService.saldosAposMovimentos(lancamentos, saldoInicial));
		model.addAttribute("relatorio", "relatorioLivroCaixa");
		return new ModelAndView("fragmentos/layoutRelatorio", model);
	}

}
