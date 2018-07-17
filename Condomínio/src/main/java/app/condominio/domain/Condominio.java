package app.condominio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import app.condominio.domain.enums.Estado;
import app.condominio.domain.validators.CNPJ;

@Entity
@Table(name = "Condominios")
public class Condominio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idcondominio")
	private Long idCondominio;

	@NotBlank
	@Size(min = 1, max = 100)
	@Column(name="razaosocial")
	private String razaoSocial;

	@CNPJ
	private String cnpj;

	@Size(max = 14)
	private String ie;

	@Size(max = 30)
	private String im;

	@Email
	@Size(max = 100)
	private String email;

	@Size(max = 10)
	private String telefone;

	@Size(max = 11)
	private String celular;

	@NotBlank
	@Size(min = 1, max = 100)
	private String endereco;

	@NotBlank
	@Size(min = 1, max = 6)
	@Column(name="numeroend")
	private String numeroEnd;

	@Size(max = 30)
	@Column(name="complementoend")
	private String complementoEnd;

	@NotBlank
	@Size(min = 1, max = 30)
	private String bairro;

	@NotBlank
	@Size(min = 1, max = 30)
	private String cidade;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Estado estado;

	@NotBlank
	@Size(min = 8, max = 8)
	private String cep;

	public Long getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Long idCondominio) {
		this.idCondominio = idCondominio;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumeroEnd() {
		return numeroEnd;
	}

	public void setNumeroEnd(String numeroEnd) {
		this.numeroEnd = numeroEnd;
	}

	public String getComplementoEnd() {
		return complementoEnd;
	}

	public void setComplementoEnd(String complementoEnd) {
		this.complementoEnd = complementoEnd;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCondominio == null) ? 0 : idCondominio.hashCode());
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
		Condominio other = (Condominio) obj;
		if (idCondominio == null) {
			if (other.idCondominio != null)
				return false;
		} else if (!idCondominio.equals(other.idCondominio))
			return false;
		return true;
	}

}