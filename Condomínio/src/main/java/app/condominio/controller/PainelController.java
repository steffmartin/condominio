package app.condominio.controller;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.condominio.domain.Periodo;
import app.condominio.service.CobrancaService;
import app.condominio.service.ContaService;
import app.condominio.service.MovimentoService;
import app.condominio.service.OrcamentoService;
import app.condominio.service.PeriodoService;

@Controller
@RequestMapping("/sindico")
public class PainelController {

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "painel", "" };
	}

	// FIXME migrar estes métodos para o relatorioService.
	@Autowired
	ContaService contaService;

	@Autowired
	CobrancaService cobrancaService;

	@Autowired
	MovimentoService movimentoService;

	@Autowired
	PeriodoService periodoService;

	@Autowired
	OrcamentoService orcamentoService;

	@GetMapping({ "/", "", "/painel", "/dashboard" })
	public ModelAndView sindico(ModelMap model) {

		// Informações do dashboard
		model.addAttribute("saldoAtual", contaService.saldoAtual());
		model.addAttribute("inadimplencia", cobrancaService.inadimplencia());

		YearMonth mesAtual = YearMonth.from(LocalDate.now());
		model.addAttribute("receitaDespesaMes",
				movimentoService.receitaDespesaEntre(mesAtual.atDay(1), mesAtual.atEndOfMonth()));

		Periodo periodoAtual = periodoService.ler(LocalDate.now());
		model.addAttribute("receitaDespesaRealizada", movimentoService.receitaDespesa(periodoAtual));
		model.addAttribute("receitaDespesaOrcada", orcamentoService.totalOrcado(periodoAtual));
		// Até aqui

		model.addAttribute("conteudo", "painel");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

}
