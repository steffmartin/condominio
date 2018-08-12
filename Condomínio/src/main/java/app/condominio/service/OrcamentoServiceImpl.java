package app.condominio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.OrcamentoDao;
import app.condominio.domain.Orcamento;

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
		return orcamentoDao.findAllByPeriodoIn(periodoService.listar());
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
		if (entidade.getPeriodo() != null) {
			// Só permitir se o período estiver aberto
			if (entidade.getPeriodo().getEncerrado()) {
				validacao.rejectValue("periodo", "Final");
			}
			// Não pode ter orçamento repetido
			else if (entidade.getSubcategoria() != null && ((entidade.getIdOrcamento() == null
					&& orcamentoDao.existsByPeriodoAndSubcategoria(entidade.getPeriodo(), entidade.getSubcategoria()))
					|| (entidade.getIdOrcamento() != null
							&& orcamentoDao.existsByPeriodoAndSubcategoriaAndIdOrcamentoNot(entidade.getPeriodo(),
									entidade.getSubcategoria(), entidade.getIdOrcamento())))) {
				validacao.rejectValue("subcategoria", "Unique");
			}
		}
	}

}
