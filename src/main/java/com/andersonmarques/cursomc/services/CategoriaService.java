package com.andersonmarques.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Categoria;
import com.andersonmarques.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	//Essa anotação Autowired instância automaticamente a classe CategoriaRepository
	@Autowired 
	private CategoriaRepository repositorio;
	
	//Faz a busca no repositorio com base no id
	public Categoria buscar(Integer id) {
		Optional<Categoria> objetoRecebido = repositorio.findById(id);
		return objetoRecebido.orElse(null);
	}

}
