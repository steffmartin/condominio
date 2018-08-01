package app.condominio.domain.enums;

public enum MotivoEmissao {

	// @formatter:off
	O("Normal", "Ordinária"),
	E("Avulsa", "Extraordinária"),
	R("Renegociação", null);
	// @formatter:on

	private final String nome;
	
	private final String sinonimo;

	private MotivoEmissao(String nome, String sinonimo) {
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
