package app.condominio.service;

import java.math.BigDecimal;

import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;
import app.condominio.domain.enums.TipoCategoria;

public interface OrcamentoService extends CrudService<Orcamento, Long> {

	public BigDecimal somaOrcamentos(Periodo periodo, TipoCategoria tipo);

}
