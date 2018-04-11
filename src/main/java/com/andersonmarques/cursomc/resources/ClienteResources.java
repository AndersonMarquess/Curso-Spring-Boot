package com.andersonmarques.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.services.ClienteService;

//Anotação do controlador rest
@RestController
@RequestMapping(value="/clientes")
public class ClienteResources {
	
	@Autowired
	private ClienteService service;
	
	
     //Associando a função ao Rest com método de get.
	 //O value é o id que será informado na hora de buscar alguma informação
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	 //O Tipo ResponseEntity é a resposta da busca, neste caso ele informa que não sabe o que será retornado <?>
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
}
