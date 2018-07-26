package app.condominio.domain.enums;

public enum TipoRelacao {

	// @formatter:off
	P("Proprietário"),
	I("Inquilino"),
	O("Outro");
	// @formatter:on

	private final String nome;

	private TipoRelacao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
