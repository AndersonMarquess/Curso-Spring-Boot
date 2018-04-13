package com.andersonmarques.cursomc.resources;

import com.andersonmarques.cursomc.domain.Pedido;
import com.andersonmarques.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

//Anotação do controlador rest
@RestController
@RequestMapping(value="/pedidos")
public class PedidoResources {
	
	@Autowired
	private PedidoService service;
	
	
     //Associando a função ao Rest com método de get.
	 //O value é o id que será informado na hora de buscar alguma informação
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	 //O Tipo ResponseEntity é a resposta da busca
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}

	//função que vai receber em formato Json, e com a anotação transformará em um objeto se ela for valido.
	//CRIANDO um pedido
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody Pedido obj) {
		obj = service.insert(obj);

		//Retorna uma URI do novo objeto inserido
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
}
