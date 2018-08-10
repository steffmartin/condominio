package app.condominio.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "transferencias")
@PrimaryKeyJoinColumn(name = "idmovimento")
public class Transferencia extends Movimento {

	@NotNull
	private boolean saida = true;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcontainversa")
	private Conta contaInversa;

	@OneToOne
	@JoinColumn(name = "idmovimentoinverso")
	private Transferencia movimentoInverso;

	public boolean isSaida() {
		return saida;
	}

	public void setSaida(boolean saida) {
		this.saida = saida;
	}

	public Conta getContaInversa() {
		return contaInversa;
	}

	public void setContaInversa(Conta contaInversa) {
		this.contaInversa = contaInversa;
	}

	public Transferencia getMovimentoInverso() {
		return movimentoInverso;
	}

	public void setMovimentoInverso(Transferencia movimentoInverso) {
		this.movimentoInverso = movimentoInverso;
	}

}
