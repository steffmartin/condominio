package app.condominio.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import app.condominio.domain.enums.Estado;

@SuppressWarnings("serial")
@Entity
@Table(name = "pessoas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idpessoa")
	private Long idPessoa;

	@NotBlank
	@Size(min = 1, max = 50)
	private String nome;

	@Email
	@NotBlank
	@Size(min = 1, max = 100)
	private String email;

	@Size(max = 10)
	private String telefone;

	@Size(max = 11)
	private String celular;

	@Size(max = 100)
	private String endereco;

	@Size(max = 6)
	@Column(name = "numeroend")
	private String numeroEnd;

	@Size(max = 30)
	@Column(name = "complementoend")
	private String complementoEnd;

	@Size(max = 30)
	private String bairro;

	@Size(max = 30)
	private String cidade;

	@Enumerated(EnumType.STRING)
	private Estado estado;

	@Size(max = 8)
	private String cep;

	//TODO colocar este campo obrigatório
	//@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominio")
	//@Fetch(FetchMode.JOIN)
	private Condominio condominio;
	
	@OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy(value = "dataEntrada")
	@Valid
	private List<Relacao> relacoes = new ArrayList<>();

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<Relacao> getRelacoes() {
		return relacoes;
	}

	public void setRelacoes(List<Relacao> relacoes) {
		this.relacoes = relacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPessoa == null) ? 0 : idPessoa.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (idPessoa == null) {
			if (other.idPessoa != null)
				return false;
		} else if (!idPessoa.equals(other.idPessoa))
			return false;
		return true;
	}

}
