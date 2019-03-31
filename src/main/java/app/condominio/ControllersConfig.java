package app.condominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import app.condominio.domain.Usuario;
import app.condominio.service.UsuarioService;

@ControllerAdvice
public class ControllersConfig {

	@Autowired
	UsuarioService usuarioService;

	@ModelAttribute("haCondominio")
	public boolean haCondominio() {
		Usuario usuario = usuarioService.lerLogado();
		if (usuario == null) {
			return false;
		}
		return usuario.getCondominio() != null;
	}

	@InitBinder
	public void registerCustomEditors(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
