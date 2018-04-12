package com.andersonmarques.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Pedido;
import com.andersonmarques.cursomc.repositories.PedidoRepository;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	//Essa anotação Autowired instância automaticamente a classe PedidoRepository
	@Autowired 
	private PedidoRepository repositorio;
	
	//Faz a busca no repositorio com base no id
	public Pedido find(Integer id) {
		Optional<Pedido> objetoRecebido = repositorio.findById(id);
		
		//Se o objeto não for encontrado, é lançado uma exception através de uma lambda para informar o problema.
		return objetoRecebido.orElseThrow(()-> new ObjectNotFoundException("O Objeto não foi contrado, ID: "+id+
				", Pedido: "+Pedido.class.getName()));
	}
}
