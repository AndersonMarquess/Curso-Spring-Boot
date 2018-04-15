package com.andersonmarques.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationErro extends StandartError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationErro(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError (String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
