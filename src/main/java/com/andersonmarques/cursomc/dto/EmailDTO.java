package com.andersonmarques.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class EmailDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80.")
	private String email;
	
	public EmailDTO() {}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
