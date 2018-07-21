package app.condominio.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import app.condominio.domain.enums.Genero;
import app.condominio.domain.validators.CPF;

@SuppressWarnings("serial")
@Entity
@Table(name = "pessoasfisicas")
@PrimaryKeyJoinColumn(name="idpessoa")
public class PessoaFisica extends Pessoa {

	@NotBlank
	@Size(min = 1, max = 100)
	private String sobrenome;
	
	@CPF
	private String cpf;
	
	@Size(max = 14)
	private String rg;
	
	@Basic
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	
	@Enumerated(EnumType.STRING)
	private Genero genero;

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
}
