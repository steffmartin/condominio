package app.condominio.service;

import java.util.List;

import org.springframework.validation.BindingResult;

public interface CrudService<C, T> {

	public void salvar(C entidade);

	public C ler(T id);

	public List<C> listar();

	public void editar(C entidade);

	public void excluir(C entidade);
	
	public void validar(C entidade, BindingResult validacao);

}
