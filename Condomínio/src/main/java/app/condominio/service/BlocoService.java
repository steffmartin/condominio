package app.condominio.service;

import java.util.List;

import app.condominio.domain.Bloco;

public interface BlocoService {
	
	public void salvar(Bloco bloco);
	
	public Bloco ler(Long id);
	
	public List<Bloco> listar();
	
	public void editar(Bloco bloco);
	
	public void excluir(Bloco bloco);
	
	public boolean haCondominio();

}
