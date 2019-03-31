package app.condominio.domain;

import java.text.ParseException;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.swing.text.MaskFormatter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import app.condominio.domain.enums.Genero;
import app.condominio.domain.validators.CPF;

@SuppressWarnings("serial")
@Entity
@Table(name = "pessoasfisicas")
@PrimaryKeyJoinColumn(name = "idpessoa")
public class PessoaFisica extends Pessoa {

	@NotBlank
	@Size(min = 1, max = 100)
	private String sobrenome;

	@CPF
	private String cpf;

	@Size(max = 14)
	private String rg;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate nascimento;

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

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	@Override
	public String cpfCnpj() {
		if (cpf == null) {
			return null;
		} else {
			try {
				MaskFormatter formatador = new MaskFormatter("###.###.###-##");
				formatador.setValueContainsLiteralCharacters(false);
				return formatador.valueToString(cpf);
			} catch (ParseException e) {
				return cpf;
			}
		}
	}

	@Override
	public String toString() {
		if (sobrenome != null) {
			return getNome() + " " + sobrenome;
		} else {
			return super.toString();
		}

	}

}
