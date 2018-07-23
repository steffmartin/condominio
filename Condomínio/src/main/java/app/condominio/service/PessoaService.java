package app.condominio.service;

import app.condominio.domain.Pessoa;

public interface PessoaService extends CrudService<Pessoa, Long> {

	public boolean haCondominio();
}
