package app.condominio.domain.enums;

public enum MotivoBaixa {

	// @formatter:off
	N("Recebimento normal"),
	R("Renegociação de dívida"),
	P("Prescrição"),
	O("Outras");
	// @formatter:on

	private final String nome;

	private MotivoBaixa(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
