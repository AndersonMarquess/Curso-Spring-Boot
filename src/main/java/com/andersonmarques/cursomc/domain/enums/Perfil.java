package com.andersonmarques.cursomc.domain.enums;

public enum Perfil {
	//ROLE_ OBRIGATORIO
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");

	private Integer cod;
	private String descricao;
	
	private Perfil(Integer cod, String descricao)  {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Perfil e : Perfil.values()) {
			if(cod.equals(e.cod)) {
				return e;
			}
		}
		throw new IllegalArgumentException("Não foi encontrado o código informado");
	}
}
