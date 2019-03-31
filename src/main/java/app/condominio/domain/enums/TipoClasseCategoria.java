package app.condominio.domain.enums;

public enum TipoClasseCategoria {

	// @formatter:off
	C("Categoria Sintética","Categoria"),
	S("Categoria Analítica","Subcategoria");
	// @formatter:on

	private final String nome;
	private final String classe;

	private TipoClasseCategoria(String nome, String classe) {
		this.nome = nome;
		this.classe = classe;
	}

	public String getNome() {
		return nome;
	}

	public String getClasse() {
		return classe;
	}
}
