package app.condominio.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import app.condominio.service.UsuarioService;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	UsuarioService usuarioService;

	@Override
	public void initialize(UniqueUsername username) {
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext cxt) {
		return !usuarioService.existe(username);
	}
}
