package com.andersonmarques.cursomc.services.exceptions;


public class DataIntegrityException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String descException) {
		super(descException);
	}
	
	public DataIntegrityException(String descException, Throwable throwable) {
		super(descException, throwable);
	}
	
	

}
