package com.andersonmarques.cursomc.dto;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preechimento obrigatório")
	@Length(min=5, max=150, message="O Campo deve conter de 5 a 150 caracteres")
	private String nome;
	
	@NotEmpty(message="Preechimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	public ClienteDTO() {}

	public ClienteDTO(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
