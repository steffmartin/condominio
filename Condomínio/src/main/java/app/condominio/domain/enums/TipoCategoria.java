package app.condominio.domain.enums;

public enum TipoCategoria {

	// @formatter:off
	R("Receita"),
	D("Despesa");
	// @formatter:on

	private final String nome;

	private TipoCategoria(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
