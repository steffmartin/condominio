package app.condominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void enviarEmail(SimpleMailMessage email) {
		emailSender.send(email);
	}

	@Override
	public void enviarEmail(String para, String assunto, String mensagem) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(para);
		message.setSubject(assunto);
		message.setText(mensagem);
		emailSender.send(message);
	}

}
