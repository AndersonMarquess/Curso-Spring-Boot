package com.andersonmarques.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.dto.ClienteDTO;
import com.andersonmarques.cursomc.dto.ClienteNewDTO;
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
	 //O Tipo ResponseEntity é a resposta da busca, neste caso ele um cliente ou uma exception
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find (@RequestParam(value="value") String email){
		Cliente obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	
	
	//função que vai receber em formato Json, e com a anotação transformará em um objeto se ela for valida.
	//CRIANDO um novo cliente
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody ClienteNewDTO objDTO) {
		Cliente obj = service.fromDTO(objDTO);
		
		obj = service.insert(obj);
		
		//Retorna uma URI do novo objeto inserido
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	
	//função que vai receber em formato Json e ATUALIZAR o nome de uma cliente já existente
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	
	//Apenas administradores
	@PreAuthorize("hasAnyRole('ADMIN')")
	//função que vai receber em formato Json e remover o objeto
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	//Neste caso ele retorna todas os cliente cadastrado ou uma exception
	public ResponseEntity<List<ClienteDTO>> findAll () {
		
		//FindAll retorna uma LISTA de clientes
		List<Cliente> objs = service.findAll();
		
		//Faz uma lista secundaria, que vai "mapear" cada item da lista objs 
		//será criado um clienteDTO, depois o collect transforma em lista
		List<ClienteDTO> objDTOs = objs.stream().map(objeto -> new ClienteDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTOs);
	}
	
	

	@PreAuthorize("hasAnyRole('ADMIN')")
	//Vai retornar as clientes de acordo com página
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage (
			//Usa parâmetros opcionais, primeiro definimos qual é a variável que vai receber o valor e depois passamos um valor padrão
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		//FindAll retorna uma LISTA de clientes
		Page<Cliente> objs = service.findPage(page, linesPerPage, orderBy, direction);
		
		//Faz uma lista secundaria, que vai "mapear" cada item da lista objs seguindo os valores estabelecidos como parâmetro
		//será criado uma clienteDTO para cada objeto da Page
		Page<ClienteDTO> objDTOs = objs.map(objeto -> new ClienteDTO(objeto));
		return ResponseEntity.ok().body(objDTOs);
	}
	
	//Enviar imagem de perfil
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture (@RequestParam(name="file")MultipartFile multipartFile) {
		URI uri = service.uploadProfilePicture(multipartFile);
		return ResponseEntity.created(uri).build();
	}
}
