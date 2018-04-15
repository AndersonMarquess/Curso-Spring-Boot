package com.andersonmarques.cursomc.services.exceptions;


public class FileException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public FileException(String descException) {
		super(descException);
	}
	
	public FileException(String descException, Throwable throwable) {
		super(descException, throwable);
	}

}
