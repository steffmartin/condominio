package app.condominio.service;

import java.util.List;

import app.condominio.domain.Pessoa;

public interface PessoaService {

	public void salvar(Pessoa pessoa);

	public Pessoa ler(Long id);

	public List<Pessoa> listar();

	public void editar(Pessoa pessoa);

	public void excluir(Pessoa pessoa);
	
	public boolean haCondominio();
}
