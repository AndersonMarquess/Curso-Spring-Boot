package com.andersonmarques.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.andersonmarques.cursomc.domain.Cidade;

//Essa interface com a anotação @Repository, permite realizar buscas no banco de dados, ela estende do JpaRepository e informa
//Qual o tipo da classe/objeto que será buscado e qual é o ID desse objeto, neste caso nós definimos como Integer. 
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :id ORDER BY obj.nome")
	List<Cidade> findCidades(@Param("id") Integer id);
}
