package com.andersonmarques.cursomc.services.exceptions;


public class AuthorizationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String descException) {
		super(descException);
	}
	
	public AuthorizationException(String descException, Throwable throwable) {
		super(descException, throwable);
	}

}
