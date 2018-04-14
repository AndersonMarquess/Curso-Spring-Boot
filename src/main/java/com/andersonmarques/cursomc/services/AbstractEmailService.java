package com.andersonmarques.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.andersonmarques.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String emailOrigem;
	
	@Override
	public void sandOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage sMailMessage = prepareSimpleMailMessageFromPedido(pedido);
		sandEmail(sMailMessage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage simple = new SimpleMailMessage();
		simple.setTo(pedido.getCliente().getEmail());
		simple.setFrom(emailOrigem);
		simple.setSubject("Pedido confirmado! Código: "+pedido.getId());
		simple.setSentDate(new Date(System.currentTimeMillis()));
		simple.setText(pedido.toString());
		
		return simple;
	}
	
	
}
