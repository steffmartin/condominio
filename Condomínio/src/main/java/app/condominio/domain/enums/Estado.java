package app.condominio.domain.enums;

public enum Estado {
	
	// Fonte: https://github.com/caelum/caelum-stella/blob/master/stella-nfe/src/main/java/br/com/caelum/stella/nfe/Estados.java
	
	// @formatter:off
	AC(12,"Acre"),
	AL(27,"Alagoas"),
	AP(16,"Amapá"),
	AM(13,"Amazonas"),
	BA(29,"Bahia"),
	CE(23,"Ceará"),
	DF(53,"Distrito Federal"),
	ES(32,"Espírito Santo"),
	GO(52,"Goiás"),
	MA(21,"Maranhão"),
	MT(51,"Mato Grosso"),
	MS(50,"Mato Grosso do Sul"),
	MG(31,"Minas Gerais"),
	PA(15,"Pará"),
	PB(25,"Paraíba"),
	PR(41,"Paraná"),
	PE(26,"Pernambuco"),
	PI(22,"Piauí"),
	RJ(33,"Rio de Janeiro"),
	RN(24,"Rio Grande do Norte"),
	RS(43,"Rio Grande do Sul"),
	RO(11,"Rondônia"),
	RR(14,"Roraima"),
	SC(42,"Santa Catarina"),
	SP(35,"São Paulo"),
	SE(28,"Sergipe"),
	TO(17,"Tocantins");
	// @formatter:on

	private final int codigoIbge;
	private final String nome;

	private Estado(int codigoIbge, String nome) {
		this.codigoIbge = codigoIbge;
		this.nome = nome;
	}

	public String getCodigo() {
		return Integer.toString(codigoIbge);
	}

	public String getNome() {
		return nome;
	}

	public int getCodigoIbge() {
		return codigoIbge;
	}
}
