package app.condominio.service;

import app.condominio.domain.Bloco;

public interface BlocoService extends CrudService<Bloco,Long> {
	
	public boolean haCondominio();

}
