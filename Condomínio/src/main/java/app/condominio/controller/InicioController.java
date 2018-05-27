package app.condominio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import app.condominio.dao.UsuarioDao;
import app.condominio.domain.Autorizacao;
import app.condominio.domain.Usuario;

@Controller
public class InicioController {

	@Autowired
	private UsuarioDao dao;

	@GetMapping({ "/", "" })
	public String inicio() {
		return "site/inicio";
	}

	@GetMapping("teste")
	public String teste() {
		
		Usuario x = new Usuario();
		x.setNome("Arthur");
		x.setSobrenome("Boy");
		x.setEmail("art@outlook.com");
		x.setUsername("arthur");
		x.setPassword("password");
		x.getAutorizacoes().add(Autorizacao.ROLE_SINDICO);
		x.getAutorizacoes().add(Autorizacao.ROLE_MORADOR);
		System.out.println(x.toString());
		dao.create(x);
		
		Usuario u = dao.read("steffan");
		System.out.println(u.toString());
		
		Usuario y = dao.read("heitoorhn");
		System.out.println(y.toString());
		
		Usuario z = dao.read("marcelo");
		System.out.println(z.toString());
		
		Usuario k = dao.read("gabriel");
		System.out.println(k.toString());
		
		Usuario i = dao.read("ana");
		System.out.println(i.toString());
		
		Usuario j = dao.read("arthur");
		System.out.println(j.toString());
		
		return "site/inicio";
	}

}
