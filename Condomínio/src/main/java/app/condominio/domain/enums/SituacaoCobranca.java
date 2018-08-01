package app.condominio.domain.enums;

public enum SituacaoCobranca {

	// @formatter:off
	N("Normal"),
	P("Protesto"),
	J("Ação Judicial"),
	O("Outras");
	// @formatter:on

	private final String nome;

	private SituacaoCobranca(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
