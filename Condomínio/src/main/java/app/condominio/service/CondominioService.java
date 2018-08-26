package app.condominio.service;

import app.condominio.domain.Condominio;

public interface CondominioService extends CrudService<Condominio, Long> {

	public Condominio ler();

}
