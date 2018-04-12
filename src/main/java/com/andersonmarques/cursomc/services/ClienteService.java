package com.andersonmarques.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.dto.ClienteDTO;
import com.andersonmarques.cursomc.repositories.ClienteRepository;
import com.andersonmarques.cursomc.services.exceptions.DataIntegrityException;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//Essa anotação Autowired instância automaticamente a classe ClienteRepository
	@Autowired 
	private ClienteRepository repositorio;
	
	//Faz a busca no repositorio com base no id
	public Cliente find(Integer id) {
		Optional<Cliente> objetoRecebido = repositorio.findById(id);
		
		//Se o objeto não for encontrado, é lançado uma exception através de uma lambda para informar o problema.
		return objetoRecebido.orElseThrow(()-> new ObjectNotFoundException("O Objeto não foi contrado, ID: "+id+
				", Cliente: "+Cliente.class.getName()));
	}
	
	//Atualiza a categoria no repositório
	public Cliente update(Cliente obj) {
		Cliente clienteExistente = find(obj.getId());
		
		//Atualiza os campos do cliente antigo com base no novo objeto
		updateData(clienteExistente, obj);
		return repositorio.save(clienteExistente);
	}
	
	//Remover uma categoria com base no ID
	public void delete (Integer id) {
		find(id);
		try {
			repositorio.deleteById(id);
		}catch(DataIntegrityViolationException erro) {
			throw new DataIntegrityException("Não é possível remover cliente com entidades relacionadas");
		}
	}
	
	//Retorna todas as categorias
	public List<Cliente> findAll(){
		return repositorio.findAll();
	}
		
	//Buscar informações das categorias dividido em paginação
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		//É preciso fazer a conversão de String para Direction na hora de informar o valor
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
	}
	
	//Retorna uma categoria a partir de um DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente clienteExistente, Cliente novo) {
		clienteExistente.setNome(novo.getNome());
		clienteExistente.setEmail(novo.getEmail());
	}
}
