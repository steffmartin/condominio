package app.condominio.domain.enums;

public enum TipoCategoria {

	// @formatter:off
	R("Receita","Entrada"),
	D("Despesa","Saída");
	// @formatter:on

	private final String nome;
	private final String movimento;

	private TipoCategoria(String nome, String movimento) {
		this.nome = nome;
		this.movimento = movimento;
	}

	public String getNome() {
		return nome;
	}

	public String getMovimento() {
		return movimento;
	}
}
