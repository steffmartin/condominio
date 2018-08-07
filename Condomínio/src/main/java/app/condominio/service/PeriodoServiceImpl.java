package app.condominio.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import app.condominio.dao.PeriodoDao;
import app.condominio.domain.Condominio;
import app.condominio.domain.Periodo;

@Service
@Transactional
public class PeriodoServiceImpl implements PeriodoService {

	@Autowired
	private PeriodoDao periodoDao;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Periodo entidade) {
		entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		periodoDao.save(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Periodo ler(Long id) {
		return periodoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Periodo> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<>();
		}
		return condominio.getPeriodos();
	}

	// TODO trocar por consulta SQL estes 2 métodos
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean haPeriodo(LocalDate data) {
		List<Periodo> periodos = listar();
		for (Periodo p : periodos) {
			if ((p.getInicio().isBefore(data) || p.getInicio().isEqual(data))
					&& (p.getFim().isEqual(data) || p.getFim().isAfter(data))) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Periodo ler(LocalDate data) {
		List<Periodo> periodos = listar();
		for (Periodo p : periodos) {
			if ((p.getInicio().isBefore(data) || p.getInicio().isEqual(data))
					&& (p.getFim().isEqual(data) || p.getFim().isAfter(data))) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void editar(Periodo entidade) {
		periodoDao.save(entidade);
	}

	@Override
	public void excluir(Periodo entidade) {
		periodoDao.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Periodo entidade, BindingResult validacao) {
		// LATER ver se haverá validação de bloco a fazer

	}

}
