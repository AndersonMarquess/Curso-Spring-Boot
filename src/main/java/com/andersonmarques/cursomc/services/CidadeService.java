package com.andersonmarques.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Cidade;
import com.andersonmarques.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findAll(Integer idEstado){
		return cidadeRepository.findCidades(idEstado);
	}

}
