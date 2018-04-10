package com.andersonmarques.cursomc.services.exceptions;


public class ObjectNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String descException) {
		super(descException);
	}
	
	public ObjectNotFoundException(String descException, Throwable throwable) {
		super(descException, throwable);
	}
	
	

}
