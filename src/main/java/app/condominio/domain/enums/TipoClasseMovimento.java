package app.condominio.domain.enums;

public enum TipoClasseMovimento {

	// @formatter:off
	L("Lançamento"),
	T("Transferência");
	// @formatter:on

	private final String nome;

	private TipoClasseMovimento(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
