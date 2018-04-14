package com.andersonmarques.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.andersonmarques.cursomc.domain.Cliente;
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
	
	@Override
	public void sandNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage simples = prepareNewPasswordEmail(cliente, newPass);
		sandEmail(simples);
	}

	protected SimpleMailMessage prepareNewPasswordEmail (Cliente cliente, String newPass) {
		SimpleMailMessage simple = new SimpleMailMessage();
		simple.setTo(cliente.getEmail());
		simple.setFrom(emailOrigem);
		simple.setSubject("Nova senha");
		simple.setSentDate(new Date(System.currentTimeMillis()));
		simple.setText("Sua nova senha gerada de forma aleatória: "+newPass);
		
		return simple;
	}
	
}
