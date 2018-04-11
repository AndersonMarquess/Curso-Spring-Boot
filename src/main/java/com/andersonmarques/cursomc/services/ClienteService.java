package com.andersonmarques.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.repositories.ClienteRepository;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//Essa anotação Autowired instância automaticamente a classe ClienteRepository
	@Autowired 
	private ClienteRepository repositorio;
	
	//Faz a busca no repositorio com base no id
	public Cliente buscar(Integer id) {
		Optional<Cliente> objetoRecebido = repositorio.findById(id);
		
		//Se o objeto não for encontrado, é lançado uma exception através de uma lambda para informar o problema.
		return objetoRecebido.orElseThrow(()-> new ObjectNotFoundException("O Objeto não foi contrado, ID: "+id+
				", Cliente: "+Cliente.class.getName()));
	}
}
