package app.condominio.service;

import java.util.List;

import app.condominio.domain.Moradia;

public interface MoradiaService {

	public void salvar(Moradia moradia);

	public Moradia ler(Long id);

	public List<Moradia> listar();

	public void editar(Moradia moradia);

	public void excluir(Moradia moradia);
}
