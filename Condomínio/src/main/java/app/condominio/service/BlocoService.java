package app.condominio.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import app.condominio.domain.Bloco;
import app.condominio.domain.Usuario;

public interface BlocoService {
	
	public void salvar(Bloco bloco);
	
	public Bloco ler(Long id);
	
	public List<Bloco> listar();
	
	public void editar(Bloco bloco);
	
	public void excluir(Bloco bloco);
	
	public boolean haCondominio();

}
