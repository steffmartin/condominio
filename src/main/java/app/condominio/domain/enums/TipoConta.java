package app.condominio.domain.enums;

public enum TipoConta {

	// @formatter:off
	CX("Caixa"),
	BC("Conta Bancária");
	// @formatter:on

	private final String nome;

	private TipoConta(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
