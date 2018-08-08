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

	// DECIMAL(9,2) no MySQL
	@NotNull
	private BigDecimal saldo = BigDecimal.ZERO;

	// TODO colocar este campo obrigatório
	// @NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominio")
	// @Fetch(FetchMode.JOIN)
	private Condominio condominio;

	@OneToMany(mappedBy = "conta", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	// @OrderBy(value = "data, subcategoria.categoriaPai.ordem,
	// subcategoria.descricao")
	private List<Lancamento> lancamentos = new ArrayList<>();

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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
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
