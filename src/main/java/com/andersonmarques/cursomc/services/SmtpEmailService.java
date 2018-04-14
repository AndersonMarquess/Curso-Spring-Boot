package com.andersonmarques.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {
	
	//Não fiz a configuração para enviar o email com o gmail no properties
	
//	@Autowired
//	private EmailService emailService;
	
	private static final Logger log = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void sandEmail(SimpleMailMessage email) {
		log.info("Simulando envio de email");
//		emailService.sandEmail(email);
		log.info("Email enviado");
	}
	
	

}
