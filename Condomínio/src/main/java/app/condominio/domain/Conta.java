package app.condominio.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "contas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Conta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idconta")
	private Long idConta;

	@Size(min = 1, max = 2)
	@NotBlank
	private String sigla;

	@Size(max = 30)
	private String descricao;

	@NotNull
	@Column(name = "saldoinicial")
	private BigDecimal saldoInicial;

	// TODO fazer procedure para atualizar saldo ao inserir, editar ou excluir
	// movimentos
	@Column(name = "saldoatual")
	private BigDecimal saldoAtual;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominio")
	private Condominio condominio;

	@OneToMany(mappedBy = "conta", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Movimento> movimentos = new ArrayList<>();

	@OneToMany(mappedBy = "contaInversa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Transferencia> transferenciasRecebidas = new ArrayList<>();

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	public List<Transferencia> getTransferenciasRecebidas() {
		return transferenciasRecebidas;
	}

	public void setTransferenciasRecebidas(List<Transferencia> transferenciasRecebidas) {
		this.transferenciasRecebidas = transferenciasRecebidas;
	}

	@Override
	public String toString() {
		if (descricao != null) {
			return sigla + " - " + descricao;
		} else {
			return sigla;
		}
	}

	public String numero() {
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idConta == null) ? 0 : idConta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Conta other = (Conta) obj;
		if (idConta == null) {
			if (other.idConta != null) {
				return false;
			}
		} else if (!idConta.equals(other.idConta)) {
			return false;
		}
		return true;
	}
}
