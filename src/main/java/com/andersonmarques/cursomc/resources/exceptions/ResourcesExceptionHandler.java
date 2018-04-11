package com.andersonmarques.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

//Anotação que permite fazer a manipulação das exceptions
@ControllerAdvice
public class ResourcesExceptionHandler {
	
	//Informa qual a classe é o gatilho desse tratamento
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound (ObjectNotFoundException e, HttpServletRequest request) {
		
		StandartError erro = new StandartError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	

}