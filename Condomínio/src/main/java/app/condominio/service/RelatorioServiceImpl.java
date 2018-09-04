package app.condominio.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.domain.Movimento;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class RelatorioServiceImpl implements RelatorioService {

	@Override
	public BigDecimal[] saldosAposMovimentos(List<Movimento> movimentos, BigDecimal saldoInicial) {
		if (saldoInicial == null) {
			saldoInicial = BigDecimal.ZERO.setScale(2);
		}
		if (!movimentos.isEmpty()) {
			BigDecimal[] saldos = new BigDecimal[movimentos.size()];
			Movimento movimento = movimentos.get(0);
			// Preenche o primeiro saldo
			if (movimento.getReducao()) {
				saldos[0] = saldoInicial.subtract(movimento.getValor());
			} else {
				saldos[0] = saldoInicial.add(movimento.getValor());
			}
			// Preenche os outros saldos
			for (int i = 1; i < saldos.length; i++) {
				movimento = movimentos.get(i);
				if (movimento.getReducao()) {
					saldos[i] = saldos[i - 1].subtract(movimento.getValor());
				} else {
					saldos[i] = saldos[i - 1].add(movimento.getValor());
				}
			}
			return saldos;
		} else {
			BigDecimal[] vazio = new BigDecimal[1];
			vazio[0] = saldoInicial;
			return vazio;
		}
	}

}
