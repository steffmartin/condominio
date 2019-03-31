package app.condominio.service;

import java.util.List;

import app.condominio.domain.Subcategoria;

public interface SubcategoriaService extends CrudService<Subcategoria, Long> {

	public int contagem();

	public List<Subcategoria> listarReceitas();

	public List<Subcategoria> listarDespesas();

}
