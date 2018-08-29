package app.condominio.controller;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import app.condominio.domain.Periodo;
import app.condominio.service.CobrancaService;
import app.condominio.service.ContaService;
import app.condominio.service.MovimentoService;
import app.condominio.service.OrcamentoService;
import app.condominio.service.PeriodoService;

@Controller
public class InicioController {

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

	@GetMapping({ "/", "", "/home", "/inicio" })
	public ModelAndView inicio() {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "inicio");
	}

	@GetMapping({ "/entrar", "/login" })
	public ModelAndView preLogin() {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "login");
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

		model.addAttribute("ativo", new String[] { "painel", "" });
		model.addAttribute("conteudo", "inicio");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}
}
