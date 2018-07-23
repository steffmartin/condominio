package app.condominio.service;

import app.condominio.domain.Conta;

public interface ContaService extends CrudService<Conta,Long> {
	
	public boolean haCondominio();
	
}
