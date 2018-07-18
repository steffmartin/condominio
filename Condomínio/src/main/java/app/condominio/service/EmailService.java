package app.condominio.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
	
	public void enviarEmail(SimpleMailMessage email);
	
	public void enviarEmail(String para, String assunto, String mensagem);

}
