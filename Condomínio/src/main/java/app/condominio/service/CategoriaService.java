package app.condominio.service;

import org.springframework.validation.BindingResult;

import app.condominio.domain.Categoria;

public interface CategoriaService extends CrudService<Categoria,Long> {
	
	public boolean haCondominio();
	
	public void validar(Categoria categoria, BindingResult validacao);

}
