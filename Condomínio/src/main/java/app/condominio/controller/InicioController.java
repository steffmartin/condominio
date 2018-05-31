package app.condominio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import app.condominio.dao.UsuarioDao;

@Controller
public class InicioController {

	@Autowired
	private UsuarioDao dao;

	@GetMapping({ "/", "" })
	public String inicio() {
		return "site/inicio";
	}

}
