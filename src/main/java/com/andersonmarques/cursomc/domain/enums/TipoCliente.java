package com.andersonmarques.cursomc.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	//Construtor de Enum é privado
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	//Busca e retorna um tipo de cliente
	public static TipoCliente toEnum (Integer cod) {
		if(cod == null) {
			return null;
		}
		
		//Corre os dois valores de Pessoa
		for(TipoCliente x: TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Não foi possível encontrar o cliten do tipo especificado");
	}
}
