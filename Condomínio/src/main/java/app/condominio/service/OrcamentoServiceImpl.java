package app.condominio.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.OrcamentoDao;
import app.condominio.domain.Orcamento;
import app.condominio.domain.Periodo;
import app.condominio.domain.enums.TipoCategoria;

@Service
@Transactional
public class OrcamentoServiceImpl implements OrcamentoService {

	@Autowired
	private OrcamentoDao orcamentoDao;

	@Autowired
	private PeriodoService periodoService;

	@Override
	public void salvar(Orcamento entidade) {
		orcamentoDao.save(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Orcamento ler(Long id) {
		return orcamentoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Orcamento> listar() {
		return orcamentoDao
				.findAllByPeriodoInOrderByPeriodo_InicioAscSubcategoria_CategoriaPai_OrdemAscSubcategoria_DescricaoAsc(
						periodoService.listar());
	}

	@Override
	public void editar(Orcamento entidade) {
		orcamentoDao.save(entidade);

	}

	@Override
	public void excluir(Orcamento entidade) {
		orcamentoDao.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Orcamento entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdOrcamento() == null) {
			// Não permitir incluir orçamento repetido
			if (entidade.getPeriodo() != null && entidade.getSubcategoria() != null
					&& orcamentoDao.existsByPeriodoAndSubcategoria(entidade.getPeriodo(), entidade.getSubcategoria())) {
				validacao.rejectValue("subcategoria", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// Não permitir um orçamento repetido
			if (entidade.getPeriodo() != null && entidade.getSubcategoria() != null
					&& orcamentoDao.existsByPeriodoAndSubcategoriaAndIdOrcamentoNot(entidade.getPeriodo(),
							entidade.getSubcategoria(), entidade.getIdOrcamento())) {
				validacao.rejectValue("subcategoria", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
		if (entidade.getPeriodo() != null && entidade.getPeriodo().getEncerrado()) {
			validacao.rejectValue("periodo", "Final");
		}
	}

	@Override
	public void padronizar(Orcamento entidade) {
		// Nada a padronizar por enquanto

	}

	@Override
	public BigDecimal[] totalOrcado(Periodo periodo) {
		BigDecimal[] resultado = new BigDecimal[2];
		if (periodo != null) {
			resultado[0] = orcamentoDao.sumByPeriodoAndSubcategoria_CategoriaPai_Tipo(periodo, TipoCategoria.R);
			resultado[1] = orcamentoDao.sumByPeriodoAndSubcategoria_CategoriaPai_Tipo(periodo, TipoCategoria.D);
		}
		if (resultado[0] == null) {
			resultado[0] = BigDecimal.ZERO.setScale(2);
		}
		if (resultado[1] == null) {
			resultado[1] = BigDecimal.ZERO.setScale(2);
		}
		return resultado;
	}

}
