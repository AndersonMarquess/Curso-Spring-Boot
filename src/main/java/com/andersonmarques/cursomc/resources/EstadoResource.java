package com.andersonmarques.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmarques.cursomc.domain.Cidade;
import com.andersonmarques.cursomc.domain.Estado;
import com.andersonmarques.cursomc.dto.CidadeDTO;
import com.andersonmarques.cursomc.services.CidadeService;
import com.andersonmarques.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Estado>> findAll(){
		List<Estado> estados = estadoService.findAll();
		
		return ResponseEntity.ok().body(estados);
	}
	
	
	//Buscar cidades
	@RequestMapping(value="/{estado_id}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estado_id){
		List<Cidade> objs = cidadeService.findAll(estado_id);
		List<CidadeDTO> objDTOs = objs.stream().map(objeto -> new CidadeDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTOs);
	}
}
