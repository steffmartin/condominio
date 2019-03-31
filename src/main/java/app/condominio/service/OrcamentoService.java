package app.condominio.service;

import java.math.BigDecimal;

import app.condominio.domain.Categoria;
import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;
import app.condominio.domain.Subcategoria;
import app.condominio.domain.enums.TipoCategoria;

public interface OrcamentoService extends CrudService<Orcamento, Long> {

	public BigDecimal somaOrcamentos(Periodo periodo, TipoCategoria tipo);

	public BigDecimal somaOrcamentos(Periodo periodo, Categoria categoria);

	public Orcamento ler(Periodo periodo, Subcategoria subcategoria);

}
