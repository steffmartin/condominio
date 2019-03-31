package app.condominio.domain.enums;

public enum TipoMoradia {

	// @formatter:off
	CA("Casa"),
	SO("Sobrado"),
	AP("Apartamento"),
	GI("Giardino"),
	CD("Cobertura Duplex"),
	CP("Penthouse"),
	DU("Duplex"),
	LO("Loft"),
	ST("Studio"),
	KT("Kitnet"),
	TH("Townhouse");
	// @formatter:on

	private final String nome;

	private TipoMoradia(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
