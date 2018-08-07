package app.condominio.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "subcategorias")
public class Subcategoria implements Serializable {

	// https://api.jquery.com/load/
	// Usar Ajax para mostrar as subcategorias de uma categoria

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsubcategoria")
	private Long idSubcategoria;

	@Size(min = 1, max = 50)
	@NotBlank
	private String descricao;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idcategoria")
	// @Fetch(FetchMode.JOIN)
	private Categoria categoriaPai;

	@OneToMany(mappedBy = "subcategoria", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "periodo.inicio, periodo.fim")
	private List<Orcamento> orcamentos = new ArrayList<>();

	@OneToMany(mappedBy = "subcategoria", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "data")
	private List<Lancamento> lancamentos = new ArrayList<>();

	public Long getIdSubcategoria() {
		return idSubcategoria;
	}

	public void setIdSubcategoria(Long idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}

	// Método necessário para uso do Thymeleaf
	public void setIdCategoria(Long idSubcategoria) {
		setIdSubcategoria(idSubcategoria);
	}

	// Método necessário para uso do Thymeleaf
	public Long getIdCategoria() {
		return getIdSubcategoria();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Orcamento> getOrcamentos() {
		return orcamentos;
	}

	public void setOrcamentos(List<Orcamento> orcamentos) {
		this.orcamentos = orcamentos;
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
		result = prime * result + ((idSubcategoria == null) ? 0 : idSubcategoria.hashCode());
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
		Subcategoria other = (Subcategoria) obj;
		if (idSubcategoria == null) {
			if (other.idSubcategoria != null) {
				return false;
			}
		} else if (!idSubcategoria.equals(other.idSubcategoria)) {
			return false;
		}
		return true;
	}
}
