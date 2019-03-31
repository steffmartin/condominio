package app.condominio.domain.enums;

public enum MotivoEmissao {

	// @formatter:off
	O("Normal"),
	E("Extra"),
	A("Avulsa"),
	R("Renegociação");
	// @formatter:on

	private final String nome;

	private MotivoEmissao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
