package com.andersonmarques.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andersonmarques.cursomc.domain.Pedido;
import com.andersonmarques.cursomc.services.PedidoService;

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
	
	//Vai retornar as Pedidos de acordo com página
		@RequestMapping(method=RequestMethod.GET)
		public ResponseEntity<Page<Pedido>> findPage (
			//Usa parâmetros opcionais, primeiro definimos qual é a variável que vai receber o valor e depois passamos um valor padrão
				@RequestParam(value="page", defaultValue="0") Integer page, 
				@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
				@RequestParam(value="orderBy", defaultValue="instante") String orderBy, 
				@RequestParam(value="direction", defaultValue="DESC") String direction) {
			
			//FindAll retorna uma LISTA de Pedidos
			Page<Pedido> objs = service.findPage(page, linesPerPage, orderBy, direction);
			return ResponseEntity.ok().body(objs);
		}
}
