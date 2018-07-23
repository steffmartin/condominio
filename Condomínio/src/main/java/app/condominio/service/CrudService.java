package app.condominio.service;

import java.util.List;

public interface CrudService<T,I> {

	public void salvar(T entidade);

	public T ler(I id);

	public List<T> listar();

	public void editar(T entidade);

	public void excluir(T entidade);

}
