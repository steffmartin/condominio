package app.condominio.domain.enums;

public enum TipoClasseCategoria {
	
	// @formatter:off
	C("Categoria (Sintética)"),
	S("Subcategoria (Analítica)");
	// @formatter:on

	private final String nome;

	private TipoClasseCategoria(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
