package com.andersonmarques.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andersonmarques.cursomc.domain.Cidade;
import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.domain.Endereco;
import com.andersonmarques.cursomc.domain.enums.Perfil;
import com.andersonmarques.cursomc.domain.enums.TipoCliente;
import com.andersonmarques.cursomc.dto.ClienteDTO;
import com.andersonmarques.cursomc.dto.ClienteNewDTO;
import com.andersonmarques.cursomc.repositories.ClienteRepository;
import com.andersonmarques.cursomc.repositories.EnderecoRepository;
import com.andersonmarques.cursomc.security.UserSS;
import com.andersonmarques.cursomc.services.exceptions.AuthorizationException;
import com.andersonmarques.cursomc.services.exceptions.DataIntegrityException;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//Essa anotação Autowired instância automaticamente a classe ClienteRepository
	@Autowired 
	private ClienteRepository repositorio;
	@Autowired 
	private EnderecoRepository enderecoRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEnconder;
	
	//Faz a busca no repositório com base no id
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if((user == null || !user.hasRole(Perfil.ADMIN))&& !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> objetoRecebido = repositorio.findById(id);
		
		//Se o objeto não for encontrado, é lançado uma exception através de uma lambda para informar o problema.
		return objetoRecebido.orElseThrow(()-> new ObjectNotFoundException("O Objeto não foi contrado, ID: "+id+
				", Cliente: "+Cliente.class.getName()));
	}
	
	
	//Salva o cliente no repositório
	@Transactional
	public Cliente insert(Cliente obj) {
		//Torna o id do obj nulo para ele ser adicionado como novo.
		obj.setId(null);
		obj =  repositorio.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj; 
	}
	
	//Atualiza o cliente no repositório
	public Cliente update(Cliente obj) {
		Cliente clienteExistente = find(obj.getId());
		
		//Atualiza os campos do cliente antigo com base no novo objeto
		updateData(clienteExistente, obj);
		return repositorio.save(clienteExistente);
	}
	
	//Remover um cliente com base no ID
	public void delete (Integer id) {
		find(id);
		try {
			repositorio.deleteById(id);
		}catch(DataIntegrityViolationException erro) {
			throw new DataIntegrityException("Não é possível remover cliente com entidades relacionadas");
		}
	}
	
	//Retorna todas os cliente
	public List<Cliente> findAll(){
		return repositorio.findAll();
	}
		
	//Buscar informações dos cliente dividido em paginação
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		//É preciso fazer a conversão de String para Direction na hora de informar o valor
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
	}
	
	//Retorna um cliente a partir de um DTO
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli1 = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), 
				TipoCliente.toEnum(objDTO.getTipo()), 
				passwordEnconder.encode(objDTO.getSenha()));
		
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getBairro(), 
				objDTO.getCep(), cli1, cid);
		
		cli1.getEnderecos().add(end);
		cli1.getTelefones().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2() != null) {
			cli1.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli1.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli1;
	}
	
	private void updateData(Cliente clienteExistente, Cliente novo) {
		clienteExistente.setNome(novo.getNome());
		clienteExistente.setEmail(novo.getEmail());
	}
}
