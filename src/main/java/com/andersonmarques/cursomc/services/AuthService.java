package com.andersonmarques.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.repositories.ClienteRepository;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEnconder;

	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sandNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(passwordEnconder.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sandNewPasswordEmail(cliente, newPass);
	}

	//Cria uma senha
	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opc = random.nextInt(3);
		if(opc == 0) {//gera número
			return (char) (random.nextInt(10) + 48);
		}else if(opc == 1) { //gera letra Maiúscula
			return (char) (random.nextInt(26) + 65);
		} else { //gera letra minúscula
			return (char) (random.nextInt(26) + 72);
		}
	}
}
