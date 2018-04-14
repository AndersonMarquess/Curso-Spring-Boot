package com.andersonmarques.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.andersonmarques.cursomc.services.exceptions.AuthorizationException;
import com.andersonmarques.cursomc.services.exceptions.DataIntegrityException;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

//Anotação que permite fazer a manipulação das exceptions
@ControllerAdvice
public class ResourcesExceptionHandler {
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound (ObjectNotFoundException errorException, HttpServletRequest request) {
		
		StandartError erro = new StandartError(HttpStatus.NOT_FOUND.value(), errorException.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandartError> dataIntegrity (DataIntegrityException errorException, HttpServletRequest request) {
		
		//Gera o código HTTP correto
		StandartError erro = new StandartError(HttpStatus.BAD_REQUEST.value(), errorException.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> validation (MethodArgumentNotValidException errorException, HttpServletRequest request) {
		
		//Gera o código HTTP correto
		ValidationErro erroValidation = new ValidationErro (HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		
		//Para cada campo de erro ele é adicionado na lista do erro validation e exibido como mensagem
		for(FieldError erroField : errorException.getBindingResult().getFieldErrors()) {
			erroValidation.addError(erroField.getField(), erroField.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroValidation);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandartError> authorization (AuthorizationException errorException, HttpServletRequest request) {
		
		StandartError erro = new StandartError(HttpStatus.FORBIDDEN.value(), errorException.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
}
