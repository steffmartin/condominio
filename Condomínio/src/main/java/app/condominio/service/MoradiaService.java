package app.condominio.service;

import app.condominio.domain.Moradia;

public interface MoradiaService extends CrudService<Moradia, Long> {
	
	public void setRelacaoMoradia(Moradia entidade);

}
