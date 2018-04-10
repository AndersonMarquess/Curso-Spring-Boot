package com.andersonmarques.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Anotação do controlador rest
@RestController
@RequestMapping(value="/categorias")
public class CategoriaResources {
	
	//Associando a função ao Rest com método de get
	@RequestMapping(method=RequestMethod.GET)
	public String listar() {
		return "Rest funcionando perfeitamente";
	}
}
