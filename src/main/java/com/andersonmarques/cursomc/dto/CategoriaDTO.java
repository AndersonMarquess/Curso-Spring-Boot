package com.andersonmarques.cursomc.dto;

import java.io.Serializable;

import com.andersonmarques.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {}

	public CategoriaDTO(Categoria objeto) {
		id = objeto.getId();
		nome = objeto.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
