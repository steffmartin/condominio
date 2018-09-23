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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import app.condominio.domain.enums.TipoCategoria;

@SuppressWarnings("serial")
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

	public static final int NIVEL_MAX = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcategoria")
	private Long idCategoria;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoCategoria tipo;

	@Size(min = 1, max = 50)
	@NotBlank
	private String descricao;

	@Max(NIVEL_MAX)
	private Integer nivel;

	// LATER criar método para ordenação atomática, lembrar que edição exige
	// reescrever ordem nas categorias filhas
	@Size(min = 1, max = 255)
	@NotBlank
	private String ordem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcategoriapai")
	private Categoria categoriaPai;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominio")
	private Condominio condominio;

	@OneToMany(mappedBy = "categoriaPai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Categoria> categoriasFilhas = new ArrayList<>();

	@OneToMany(mappedBy = "categoriaPai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "descricao")
	private List<Subcategoria> Subcategorias = new ArrayList<>();

	public static Integer getNivelMax() {
		return NIVEL_MAX;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public TipoCategoria getTipo() {
		return tipo;
	}

	public void setTipo(TipoCategoria tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getOrdem() {
		return ordem;
	}

	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<Categoria> getCategoriasFilhas() {
		return categoriasFilhas;
	}

	public void setCategoriasFilhas(List<Categoria> categoriasFilhas) {
		this.categoriasFilhas = categoriasFilhas;
	}

	public List<Subcategoria> getSubcategorias() {
		return Subcategorias;
	}

	public void setSubcategorias(List<Subcategoria> subcategorias) {
		Subcategorias = subcategorias;
	}

	@Override
	public String toString() {
		return ordem + " - " + descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());
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
		Categoria other = (Categoria) obj;
		if (idCategoria == null) {
			if (other.idCategoria != null) {
				return false;
			}
		} else if (!idCategoria.equals(other.idCategoria)) {
			return false;
		}
		return true;
	}

}
