package app.condominio.domain.enums;

public enum TipoContaBancaria {
	// @formatter:off
		C("Conta Corrente"),
		P("Poupança"),
		S("Conta Salário");
		// @formatter:on

	private final String nome;

	private TipoContaBancaria(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
