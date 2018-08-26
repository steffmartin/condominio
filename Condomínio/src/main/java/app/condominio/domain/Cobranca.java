package app.condominio.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import app.condominio.domain.enums.MotivoBaixa;
import app.condominio.domain.enums.MotivoEmissao;
import app.condominio.domain.enums.SituacaoCobranca;

@SuppressWarnings("serial")
@Entity
@Table(name = "cobrancas")
public class Cobranca implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcobranca")
	private Long idCobranca;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "motivoemissao")
	private MotivoEmissao motivoEmissao;

	@Size(max = 10)
	@NotBlank
	private String numero;

	@Size(max = 3)
	private String parcela;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "dataemissao")
	private LocalDate dataEmissao;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "datavencimento")
	private LocalDate dataVencimento;

	@NotNull
	@Min(0)
	private BigDecimal valor;

	@Min(0)
	private BigDecimal desconto;

	@Min(0)
	private BigDecimal abatimento;

	@Min(0)
	@Column(name = "outrasdeducoes")
	private BigDecimal outrasDeducoes;

	// Juros tem atualização no banco de dados
	@Min(0)
	@Column(name = "jurosmora")
	private BigDecimal jurosMora;

	// Multa tem atualização no banco de dados
	@Min(0)
	private BigDecimal multa;

	@Min(0)
	@Column(name = "outrosacrescimos")
	private BigDecimal outrosAcrescimos;

	// Total é atualizado no banco de dados quando Juros e Multa são atualizados
	@Min(0)
	@NotNull
	private BigDecimal total;

	@Size(max = 255)
	private String descricao;

	@Min(0)
	@Column(name = "percentualjurosmes")
	private Float percentualJurosMes;

	@Min(0)
	@Column(name = "percentualmulta")
	private Float percentualMulta;

	@Enumerated(EnumType.STRING)
	private SituacaoCobranca situacao;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "datarecebimento")
	private LocalDate dataRecebimento;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivobaixa")
	private MotivoBaixa motivoBaixa;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmoradia")
	private Moradia moradia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominio")
	private Condominio condominio;

	public Long getIdCobranca() {
		return idCobranca;
	}

	public void setIdCobranca(Long idCobranca) {
		this.idCobranca = idCobranca;
	}

	public Moradia getMoradia() {
		return moradia;
	}

	public void setMoradia(Moradia moradia) {
		this.moradia = moradia;
	}

	public MotivoEmissao getMotivoEmissao() {
		return motivoEmissao;
	}

	public void setMotivoEmissao(MotivoEmissao motivoEmissao) {
		this.motivoEmissao = motivoEmissao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getAbatimento() {
		return abatimento;
	}

	public void setAbatimento(BigDecimal abatimento) {
		this.abatimento = abatimento;
	}

	public BigDecimal getOutrasDeducoes() {
		return outrasDeducoes;
	}

	public void setOutrasDeducoes(BigDecimal outrasDeducoes) {
		this.outrasDeducoes = outrasDeducoes;
	}

	public BigDecimal getJurosMora() {
		return jurosMora;
	}

	public void setJurosMora(BigDecimal jurosMora) {
		this.jurosMora = jurosMora;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	public BigDecimal getOutrosAcrescimos() {
		return outrosAcrescimos;
	}

	public void setOutrosAcrescimos(BigDecimal outrosAcrescimos) {
		this.outrosAcrescimos = outrosAcrescimos;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getPercentualJurosMes() {
		return percentualJurosMes;
	}

	public void setPercentualJurosMes(Float percentualJurosMes) {
		this.percentualJurosMes = percentualJurosMes;
	}

	public Float getPercentualMulta() {
		return percentualMulta;
	}

	public void setPercentualMulta(Float percentualMulta) {
		this.percentualMulta = percentualMulta;
	}

	public SituacaoCobranca getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoCobranca situacao) {
		this.situacao = situacao;
	}

	public LocalDate getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(LocalDate dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public MotivoBaixa getMotivoBaixa() {
		return motivoBaixa;
	}

	public void setMotivoBaixa(MotivoBaixa motivoBaixa) {
		this.motivoBaixa = motivoBaixa;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	@Override
	public String toString() {
		String s = numero;
		if (parcela != null) {
			s += " - " + parcela;
		}
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCobranca == null) ? 0 : idCobranca.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Cobranca other = (Cobranca) obj;
		if (idCobranca == null) {
			if (other.idCobranca != null) {
				return false;
			}
		} else if (!idCobranca.equals(other.idCobranca)) {
			return false;
		}
		return true;
	}
}
