package com.andersonmarques.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Categoria;
import com.andersonmarques.cursomc.dto.CategoriaDTO;
import com.andersonmarques.cursomc.repositories.CategoriaRepository;
import com.andersonmarques.cursomc.services.exceptions.DataIntegrityException;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	//Essa anotação Autowired instância automaticamente a classe CategoriaRepository
	@Autowired 
	private CategoriaRepository repositorio;
	
	//Faz a busca no repositório com base no id
	public Categoria find (Integer id) {
		Optional<Categoria> objetoRecebido = repositorio.findById(id);
		
		//Se o objeto não for encontrado, é lançado uma exception através de uma lambda para informar o problema.
		return objetoRecebido.orElseThrow(()-> new ObjectNotFoundException("O Objeto não foi contrado, ID: "+id+
				", Categoria: "+Categoria.class.getName()));
	}
	
	
	//Salva a categoria no repositório
	public Categoria insert(Categoria obj) {
		//Torna o id do obj nulo para ele ser adicionado como novo.
		obj.setId(null);
		return repositorio.save(obj);
	}
	
	//Atualiza a categoria no repositório
	public Categoria update(Categoria obj) {
		find(obj.getId());
		//Se o id do obj não for nulo, então ele será atualizado.
		return repositorio.save(obj);
	}
	
	//Remover uma categoria com base no ID
	public void delete (Integer id) {
		find(id);
		try {
			repositorio.deleteById(id);
		}catch(DataIntegrityViolationException erro) {
			throw new DataIntegrityException("Não é possível remover uma categoria com produtos");
		}
	}
	
	//Retorna todas as categorias
	public List<Categoria> findAll(){
		return repositorio.findAll();
	}
	
	//Buscar informações das categorias dividido em paginação
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		//É preciso fazer a conversão de String para Direction na hora de informar o valor
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
	}
	
	//Retorna uma categoria a partir de um DTO
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
}
