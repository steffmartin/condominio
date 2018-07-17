package app.condominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.condominio.dao.CondominioDao;
import app.condominio.domain.Condominio;

@Service
@Transactional
public class CondominioServiceImpl implements CondominioService {

	@Autowired
	private CondominioDao condominioDao;

	@Override
	public void salvar(Condominio condominio) {
		condominioDao.save(condominio);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Condominio ler(Long id) {
		return condominioDao.findById(id).get();
	}

	@Override
	public void editar(Condominio condominio) {
		condominioDao.save(condominio);
	}

	@Override
	public void excluir(Condominio condominio) {
		condominioDao.delete(condominio);
	}

}
