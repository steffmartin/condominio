package app.condominio.domain.enums;

public enum Genero {

	// @formatter:off
	M("Masculino"),
	F("Feminino"),
	O("Outro");
	// @formatter:on

	private final String nome;

	private Genero(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
