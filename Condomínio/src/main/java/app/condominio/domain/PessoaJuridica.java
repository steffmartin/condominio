package app.condominio.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import app.condominio.domain.validators.CNPJ;

@SuppressWarnings("serial")
@Entity
@Table(name = "pessoasjuridicas")
@PrimaryKeyJoinColumn(name = "idpessoa")
public class PessoaJuridica extends Pessoa {

	@NotBlank
	@Size(min = 1, max = 100)
	@Column(name = "razaosocial")
	private String razaoSocial;

	@CNPJ
	private String cnpj;

	@Size(max = 14)
	private String ie;

	@Size(max = 30)
	private String im;

	@NotBlank
	@Size(min = 1, max = 50)
	@Column(name = "nomerepresentante")
	private String nomeRepresentante;

	@NotBlank
	@Size(min = 1, max = 100)
	@Column(name = "sobrenomerepresentante")
	private String sobrenomeRepresentante;

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getNomeRepresentante() {
		return nomeRepresentante;
	}

	public void setNomeRepresentante(String nomeRepresentante) {
		this.nomeRepresentante = nomeRepresentante;
	}

	public String getSobrenomeRepresentante() {
		return sobrenomeRepresentante;
	}

	public void setSobrenomeRepresentante(String sobrenomeRepresentante) {
		this.sobrenomeRepresentante = sobrenomeRepresentante;
	}

}
