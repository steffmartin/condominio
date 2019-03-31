package app.condominio.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "lancamentos")
@PrimaryKeyJoinColumn(name = "idmovimento")
public class Lancamento extends Movimento {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idperiodo")
	private Periodo periodo;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsubcategoria")
	private Subcategoria subcategoria;

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	@Override
	public String detalhe() {
		String s;
		if (getReducao()) {
			s = "Despesa com ";
		} else {
			s = "Receita de ";
		}
		return s + subcategoria.getDescricao().toLowerCase();
	}

}
