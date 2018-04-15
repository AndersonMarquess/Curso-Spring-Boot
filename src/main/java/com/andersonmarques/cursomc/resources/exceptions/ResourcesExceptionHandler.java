package com.andersonmarques.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.andersonmarques.cursomc.services.exceptions.AuthorizationException;
import com.andersonmarques.cursomc.services.exceptions.DataIntegrityException;
import com.andersonmarques.cursomc.services.exceptions.FileException;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

//Anotação que permite fazer a manipulação das exceptions
@ControllerAdvice
public class ResourcesExceptionHandler {
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound (ObjectNotFoundException errorException, HttpServletRequest request) {
		
		StandartError erro = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", errorException.getMessage(), request.getRequestURI()); 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandartError> dataIntegrity (DataIntegrityException errorException, HttpServletRequest request) {
		
		//Gera o código HTTP correto
		StandartError erro = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Integridade de dados", errorException.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> validation (MethodArgumentNotValidException errorException, HttpServletRequest request) {
		
		//Gera o código HTTP correto
		ValidationErro erroValidation = new ValidationErro (System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", errorException.getMessage(), request.getRequestURI());
		
		//Para cada campo de erro ele é adicionado na lista do erro validation e exibido como mensagem
		for(FieldError erroField : errorException.getBindingResult().getFieldErrors()) {
			erroValidation.addError(erroField.getField(), erroField.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroValidation);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandartError> authorization (AuthorizationException errorException, HttpServletRequest request) {
		
		StandartError erro = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso negado", errorException.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandartError> file (FileException errorException, HttpServletRequest request) {
		
		StandartError erro = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", errorException.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandartError> amazonService (AmazonServiceException errorException, HttpServletRequest request) {
		
		//Pega o HttpStatus correto da AmazonService
		HttpStatus status = HttpStatus.valueOf(errorException.getErrorCode());
		
		StandartError erro = new StandartError(System.currentTimeMillis(), status.value(), "Erro amazon service", errorException.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status.value()).body(erro);
	}
	

	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandartError> amazonCliente (AmazonClientException errorException, HttpServletRequest request) {
		
		StandartError erro = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro amazon client", errorException.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandartError> amazonS3 (AmazonS3Exception errorException, HttpServletRequest request) {
		
		StandartError erro = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro amazon s3", errorException.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
