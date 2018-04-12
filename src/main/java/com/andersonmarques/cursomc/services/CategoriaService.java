package com.andersonmarques.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Categoria;
import com.andersonmarques.cursomc.repositories.CategoriaRepository;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	//Essa anotação Autowired instância automaticamente a classe CategoriaRepository
	@Autowired 
	private CategoriaRepository repositorio;
	
	//Faz a busca no repositorio com base no id
	public Categoria buscar(Integer id) {
		Optional<Categoria> objetoRecebido = repositorio.findById(id);
		
		//Se o objeto não for encontrado, é lançado uma exception através de uma lambda para informar o problema.
		return objetoRecebido.orElseThrow(()-> new ObjectNotFoundException("O Objeto não foi contrado, ID: "+id+
				", Categoria: "+Categoria.class.getName()));
	}
	
	
	//Salva a categoria no repositorio
	public Categoria insert(Categoria obj) {
		//Torna o id do obj nulo para ele ser adicionado como novo.
		obj.setId(null);
		return repositorio.save(obj);
	}

}
