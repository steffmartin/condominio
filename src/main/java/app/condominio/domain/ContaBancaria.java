package app.condominio.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import app.condominio.domain.enums.TipoContaBancaria;

@SuppressWarnings("serial")
@Entity
@Table(name = "contasbancarias")
@PrimaryKeyJoinColumn(name = "idconta")
public class ContaBancaria extends Conta {

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoContaBancaria tipo;

	@NotBlank
	@Size(max = 3)
	private String banco;

	@NotBlank
	@Size(max = 5)
	private String agencia;

	@Column(name = "agenciadv")
	private Character agenciaDv;

	@NotBlank
	@Size(max = 20)
	private String conta;

	@Column(name = "contadv")
	private Character contaDv;

	public TipoContaBancaria getTipo() {
		return tipo;
	}

	public void setTipo(TipoContaBancaria tipo) {
		this.tipo = tipo;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Character getAgenciaDv() {
		return agenciaDv;
	}

	public void setAgenciaDv(Character agenciaDv) {
		this.agenciaDv = agenciaDv;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Character getContaDv() {
		return contaDv;
	}

	public void setContaDv(Character contaDv) {
		this.contaDv = contaDv;
	}

	@Override
	public String numero() {
		String s = banco + " " + agencia;
		if (agenciaDv != null) {
			s += "-" + agenciaDv;
		}
		s += " " + conta;
		if (contaDv != null) {
			s += "-" + contaDv;
		}
		return s;
	}

}
