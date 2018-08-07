package app.condominio.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
@Table(name = "lancamentos")
public class Lancamento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idlancamento")
	private Long idLancamento;

	// TODO colocar este campo obrigatório
	// @NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idperiodo")
	// @Fetch(FetchMode.JOIN)
	private Periodo periodo;

	// TODO colocar este campo obrigatório
	// @NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsubcategoria")
	// @Fetch(FetchMode.JOIN)
	private Subcategoria subcategoria;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate data;

	@NotNull
	@Min(0)
	private BigDecimal valor = BigDecimal.ZERO;

	@NotBlank
	@Size(max = 255)
	private String descricao;

	// TODO colocar este campo obrigatório
	// @NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idconta")
	// @Fetch(FetchMode.JOIN)
	private Conta conta;

	public Long getIdLancamento() {
		return idLancamento;
	}

	public void setIdLancamento(Long idLancamento) {
		this.idLancamento = idLancamento;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLancamento == null) ? 0 : idLancamento.hashCode());
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
		Lancamento other = (Lancamento) obj;
		if (idLancamento == null) {
			if (other.idLancamento != null) {
				return false;
			}
		} else if (!idLancamento.equals(other.idLancamento)) {
			return false;
		}
		return true;
	}
}
