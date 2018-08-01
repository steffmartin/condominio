package app.condominio.domain.enums;

public enum MotivoBaixa {

	// @formatter:off
	N("Normal","Recebimento"),
	R("Renegociação", null),
	O("Outras", null);
	// @formatter:on

	private final String nome;

	private final String sinonimo;

	private MotivoBaixa(String nome, String sinonimo) {
		this.nome = nome;
		this.sinonimo = sinonimo;
	}

	public String getNome() {
		return nome;
	}

	public String getSinonimo() {
		return sinonimo;
	}

}
