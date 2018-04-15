package com.andersonmarques.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andersonmarques.cursomc.domain.Estado;
import com.andersonmarques.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	@Autowired
	private EstadoRepository estadoRepository;
	
	//Buscar todos os estados
	public List<Estado> findAll(){
		return estadoRepository.findAll();
	}
}
