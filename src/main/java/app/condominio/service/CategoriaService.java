package app.condominio.service;

import java.util.List;

import app.condominio.domain.Categoria;

public interface CategoriaService extends CrudService<Categoria, Long> {

	public List<Categoria> listarReceitas();

	public List<Categoria> listarDespesas();

}
