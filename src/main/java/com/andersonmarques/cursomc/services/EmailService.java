package com.andersonmarques.cursomc.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.domain.Pedido;

@Service
public interface EmailService {

	void sandOrderConfirmationEmail(Pedido pedido);
	
	void sandEmail(SimpleMailMessage email);
	
	void sandNewPasswordEmail(Cliente cliente, String newPass);
}
