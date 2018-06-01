package app.condominio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.condominio.domain.Autorizacao;
import app.condominio.domain.Usuario;
import app.condominio.service.UsuarioService;

@Controller
@RequestMapping("conta")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("nova")
	public String novaConta() {
		
		Usuario u = new Usuario();
		u.setNome("teste");
		u.setSobrenome("pequeno");
		u.setEmail("teste@");
		u.setPassword("password");
		u.setUsername("teste");
		u.getAutorizacoes().add(Autorizacao.ROLE_SINDICO);
		usuarioService.salvar(u);
		
		u.getAutorizacoes().add(Autorizacao.ROLE_MORADOR);
		u.setEmail("testecerto@gmail.com");
		usuarioService.editar(u);
		
		u = usuarioService.ler("steffan");
		u.getAutorizacoes().remove(Autorizacao.ROLE_SINDICO);
		usuarioService.editar(u);
		
		usuarioService.excluir(usuarioService.ler("ana"));
		
		return "site/inicio";
	}

}
