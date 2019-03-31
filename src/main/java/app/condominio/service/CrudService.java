package app.condominio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface CrudService<C, T> {

	/**
	 * @param entidade
	 *            Entidade a ser persistida no banco de dados
	 */
	public void salvar(C entidade);

	/**
	 * @param id
	 *            ID da Entidade a ser recuperada do banco de dados
	 * @return Retorna uma instância da Entidade que possui o ID passado no
	 *         parâmetro, ou nulo caso não exista.
	 */
	public C ler(T id);

	/**
	 * @return Retorna uma lista do tipo List{@literal <}Entidade{@literal >} com
	 *         todas as Entidades. Nunca retorna nulo, se não houver Entidades
	 *         retorna uma lista vazia.
	 */
	public List<C> listar();

	/**
	 * @return Retorna uma página do tipo Page{@literal <}Entidade{@literal >} com,
	 *         no máximo, a quantidade solicidade de Entidades. Nunca retorna nulo,
	 *         se não houver Entidades retorna uma página vazia.
	 */
	public Page<C> listarPagina(Pageable pagina);

	/**
	 * @param entidade
	 *            Entidade a ser atualizada no banco de dados
	 */
	public void editar(C entidade);

	/**
	 * @param entidade
	 *            Entidade a ser excluída do banco de dados
	 */
	public void excluir(C entidade);

	/**
	 * @param entidade
	 *            Entidade a ser validada
	 * @param validacao
	 *            Uma instância de BindingResult para receber o resultado da
	 *            validação
	 */
	public void validar(C entidade, BindingResult validacao);

	/**
	 * @param entidade
	 *            Uma instância de Entidade para receber os valores padrão de seus
	 *            atributos não preenchidos
	 */
	public void padronizar(C entidade);

}
