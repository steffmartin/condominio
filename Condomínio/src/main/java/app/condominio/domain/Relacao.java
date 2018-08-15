package app.condominio.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import app.condominio.domain.enums.TipoRelacao;

@SuppressWarnings("serial")
@Entity
@Table(name = "pessoa_moradia")
public class Relacao implements Serializable {

	// Tutoriais:
	// https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
	// https://www.thoughts-on-java.org/many-relationships-additional-properties/

	@EmbeddedId
	private IdRelacao idRelacao = new IdRelacao();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idPessoa")
	@JoinColumn(name = "idpessoa")
	@Fetch(FetchMode.JOIN)
	private Pessoa pessoa;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idMoradia")
	@JoinColumn(name = "idmoradia")
	@Fetch(FetchMode.JOIN)
	private Moradia moradia;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoRelacao tipo;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "dataentrada")
	private LocalDate dataEntrada;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "datasaida")
	private LocalDate dataSaida;

	@Column(name = "participacaodono")
	@Max(100)
	@Min(0)
	private Float participacaoDono;

	@AssertTrue
	private Boolean isParticipacaoDono() {
		return this.tipo != TipoRelacao.P || this.participacaoDono > 0;

	}

	@AssertTrue
	private Boolean isDataSaida() {
		return this.dataSaida == null || this.dataEntrada == null || this.dataEntrada.isBefore(this.dataSaida)
				|| this.dataEntrada.isEqual(this.dataSaida);
	}

	public IdRelacao getIdRelacao() {
		return idRelacao;
	}

	public void setIdRelacao(IdRelacao idRelacao) {
		this.idRelacao = idRelacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
		if (pessoa != null) {
			this.idRelacao.setIdPessoa(pessoa.getIdPessoa());
		}
	}

	public Moradia getMoradia() {
		return moradia;
	}

	public void setMoradia(Moradia moradia) {
		this.moradia = moradia;
		if (moradia != null) {
			this.idRelacao.setIdMoradia(moradia.getIdMoradia());
		}
	}

	public TipoRelacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoRelacao tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Float getParticipacaoDono() {
		return participacaoDono;
	}

	public void setParticipacaoDono(Float participacaoDono) {
		this.participacaoDono = participacaoDono;
	}

	@Override
	public String toString() {
		return pessoa.toString() + ", " + tipo.name().toLowerCase() + " de " + moradia.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRelacao == null) ? 0 : idRelacao.hashCode());
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
		Relacao other = (Relacao) obj;
		if (idRelacao == null) {
			if (other.idRelacao != null) {
				return false;
			}
		} else if (!idRelacao.equals(other.idRelacao)) {
			return false;
		}
		return true;
	}

	@Embeddable
	public static class IdRelacao implements Serializable {

		@Column(name = "idpessoa")
		private Long idPessoa;

		@Column(name = "idmoradia")
		private Long idMoradia;

		public Long getIdPessoa() {
			return idPessoa;
		}

		public void setIdPessoa(Long idPessoa) {
			this.idPessoa = idPessoa;
		}

		public Long getIdMoradia() {
			return idMoradia;
		}

		public void setIdMoradia(Long idMoradia) {
			this.idMoradia = idMoradia;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((idMoradia == null) ? 0 : idMoradia.hashCode());
			result = prime * result + ((idPessoa == null) ? 0 : idPessoa.hashCode());
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
			IdRelacao other = (IdRelacao) obj;
			if (idMoradia == null) {
				if (other.idMoradia != null) {
					return false;
				}
			} else if (!idMoradia.equals(other.idMoradia)) {
				return false;
			}
			if (idPessoa == null) {
				if (other.idPessoa != null) {
					return false;
				}
			} else if (!idPessoa.equals(other.idPessoa)) {
				return false;
			}
			return true;
		}

	}
}
