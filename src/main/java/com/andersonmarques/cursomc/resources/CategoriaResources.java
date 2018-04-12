package com.andersonmarques.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.andersonmarques.cursomc.domain.Categoria;
import com.andersonmarques.cursomc.dto.CategoriaDTO;
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
	 //O Tipo ResponseEntity é a resposta da busca, neste caso ele retorna uma categoria ou uma exception
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	
	
	//função que vai receber em formato Json, e com a anotação transformará em um objeto, CRIANDO uma categoria
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert (@RequestBody Categoria obj) {
		obj = service.insert(obj);
		
		//Retorna uma URI do novo objeto inserido
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	
	
	//função que vai receber em formato Json e ATUALIZAR o nome de uma categoria já existente
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update (@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	
	
	//função que vai receber em formato Json e remover o objeto
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	//Neste caso ele retorna todas as categoria cadastradas ou uma exception
	public ResponseEntity<List<CategoriaDTO>> findAll () {
		
		//FindAll retorna uma LISTA de categorias
		List<Categoria> objs = service.findAll();
		
		//Faz uma lista secundaria, que vai "mapear" cada item da lista objs 
		//será criado uma categoriaDTO, depois o collect transforma em lista
		List<CategoriaDTO> objDTOs = objs.stream().map(objeto -> new CategoriaDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTOs);
	}
	
	
	
	//Vai retornar as categorias de acordo com página
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage (
		//Usa parâmetros opcionais, primeiro definimos qual é a variável que vai receber o valor e depois passamos um valor padrão
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		//FindAll retorna uma LISTA de categorias
		Page<Categoria> objs = service.findPage(page, linesPerPages, orderBy, direction);
		
		//Faz uma lista secundaria, que vai "mapear" cada item da lista objs seguindo os valores estabelecidos como parâmetro
		//será criado uma categoriaDTO para cada objeto da Page
		Page<CategoriaDTO> objDTOs = objs.map(objeto -> new CategoriaDTO(objeto));
		return ResponseEntity.ok().body(objDTOs);
	}
}




