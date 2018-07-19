package app.condominio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import app.condominio.domain.enums.TipoMoradia;

@SuppressWarnings("serial")
@Entity
@Table(name = "Moradias")
public class Moradia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmoradia")
	private Long idMoradia;

	@Size(min = 1, max = 10)
	@NotBlank
	private String sigla;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoMoradia tipo;

	private float area;

	@Max(100)
	@Column(name = "fracaoideal")
	private float fracaoIdeal;

	@Size(max = 30)
	private String matricula;

	private int vagas;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idbloco")
	@Fetch(FetchMode.JOIN)
	private Bloco bloco;

	public Long getIdMoradia() {
		return idMoradia;
	}

	public void setIdMoradia(Long idMoradia) {
		this.idMoradia = idMoradia;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public TipoMoradia getTipo() {
		return tipo;
	}

	public void setTipo(TipoMoradia tipo) {
		this.tipo = tipo;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public float getFracaoIdeal() {
		return fracaoIdeal;
	}

	public void setFracaoIdeal(float fracaoIdeal) {
		this.fracaoIdeal = fracaoIdeal;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		this.vagas = vagas;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMoradia == null) ? 0 : idMoradia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Moradia other = (Moradia) obj;
		if (idMoradia == null) {
			if (other.idMoradia != null)
				return false;
		} else if (!idMoradia.equals(other.idMoradia))
			return false;
		return true;
	}
}
