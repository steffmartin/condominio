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
import javax.validation.constraints.AssertTrue;
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

	@Min(0)
	private int parcela;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "dataemissao")
	private LocalDate dataEmissao = LocalDate.now();

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "datavencimento")
	private LocalDate dataVencimento;

	// DECIMAL(9,2) no MySQL
	@NotNull
	@Min(0)
	private BigDecimal valor = BigDecimal.ZERO;

	@NotNull
	@Min(0)
	private BigDecimal desconto = BigDecimal.ZERO;

	@NotNull
	@Min(0)
	private BigDecimal abatimento = BigDecimal.ZERO;

	@NotNull
	@Min(0)
	@Column(name = "outrasdeducoes")
	private BigDecimal outrasDeducoes = BigDecimal.ZERO;

	@NotNull
	@Min(0)
	@Column(name = "jurosmora")
	private BigDecimal jurosMora = BigDecimal.ZERO;

	@NotNull
	@Min(0)
	private BigDecimal multa = BigDecimal.ZERO;

	@NotNull
	@Min(0)
	@Column(name = "outrosacrescimos")
	private BigDecimal outrosAcrescimos = BigDecimal.ZERO;

	@Min(0)
	@NotNull
	private BigDecimal total = BigDecimal.ZERO;

	@Size(max = 255)
	private String historico;

	@Min(0)
	@Column(name = "percentualjuromes")
	private float percentualJuroMes;

	@Min(0)
	@Column(name = "percentualmulta")
	private float percentualMulta;

	@NotNull
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
	//@Fetch(FetchMode.JOIN)
	private Moradia moradia;
	
	//TODO colocar este campo obrigatório
	//@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominio")
	//@Fetch(FetchMode.JOIN)
	private Condominio condominio;

	@AssertTrue
	private boolean isTotal() {
		try {
			return total.equals(valor.subtract(desconto).subtract(abatimento).subtract(outrasDeducoes).add(jurosMora)
					.add(multa).add(outrosAcrescimos));
		} catch (NullPointerException e) {
			return false;
		}
	}

	@AssertTrue
	private boolean isDataRecebimento() {
		return dataRecebimento == null || dataRecebimento.equals(dataEmissao) || dataRecebimento.isAfter(dataEmissao);
	}

	@AssertTrue
	private boolean isMotivoBaixa() {
		return (dataRecebimento == null && motivoBaixa == null) || (dataRecebimento != null && motivoBaixa != null);
	}

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

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
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

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public float getPercentualJurosMes() {
		return percentualJuroMes;
	}

	public void setPercentualJurosMes(float percentualJurosMes) {
		this.percentualJuroMes = percentualJurosMes;
	}

	public float getPercentualMulta() {
		return percentualMulta;
	}

	public void setPercentualMulta(float percentualMulta) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCobranca == null) ? 0 : idCobranca.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cobranca other = (Cobranca) obj;
		if (idCobranca == null) {
			if (other.idCobranca != null)
				return false;
		} else if (!idCobranca.equals(other.idCobranca))
			return false;
		return true;
	}
}
