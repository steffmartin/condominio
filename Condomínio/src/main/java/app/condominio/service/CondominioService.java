package app.condominio.service;

import app.condominio.domain.Condominio;

public interface CondominioService {

	public void salvar(Condominio condominio);

	public Condominio ler(Long id);

	public void editar(Condominio condominio);

	public void excluir(Condominio condominio);

}
