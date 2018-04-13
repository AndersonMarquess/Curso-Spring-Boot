package com.andersonmarques.cursomc.services;

import com.andersonmarques.cursomc.domain.Categoria;
import com.andersonmarques.cursomc.domain.Produto;
import com.andersonmarques.cursomc.repositories.CategoriaRepository;
import com.andersonmarques.cursomc.repositories.ProdutoRepository;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
	
	//Essa anotação Autowired instância automaticamente a classe ProdutoRepository
	@Autowired 
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//Faz a busca no produtoRepository com base no id
	public Produto find(Integer id) {
		Optional<Produto> objetoRecebido = produtoRepository.findById(id);
		
		//Se o objeto não for encontrado, é lançado uma exception através de uma lambda para informar o problema.
		return objetoRecebido.orElseThrow(()-> new ObjectNotFoundException("O Objeto não foi contrado, ID: "+id+
				", Produto: "+Produto.class.getName()));
	}


	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		//É preciso fazer a conversão de String para Direction na hora de informar o valor
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias =  categoriaRepository.findAllById(ids);

		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

	}
}
