package com.andersonmarques.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andersonmarques.cursomc.domain.Categoria;
import com.andersonmarques.cursomc.services.CategoriaService;

//Anotação do controlador rest
@RestController
@RequestMapping(value="/categorias")
public class CategoriaResources {
	
	@Autowired
	private CategoriaService service;
	
	
     //Associando a função ao Rest com método de get.
	 //O value é o id que será informado na hora de buscar alguma informação
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	 //O Tipo ResponseEntity é a resposta da busca, neste caso ele informa que não sabe o que será retornado <?>
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	
	//Categoria que vai receber em formato Json, e com a anotação transformará em um objeto
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert (@RequestBody Categoria obj) {
		obj = service.insert(obj);
		
		//Retorna uma URI do novo objeto inserido
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
