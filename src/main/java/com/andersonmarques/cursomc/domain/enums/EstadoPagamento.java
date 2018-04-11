package com.andersonmarques.cursomc.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pagamento pendente"),
	QUITADO(2, "Pagamento quitado"),
	CANCELADO(3, "Pagamento canelado");

	private Integer cod;
	private String descricao;
	
	private EstadoPagamento(Integer cod, String descricao)  {
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
	
	public static EstadoPagamento toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(EstadoPagamento e : EstadoPagamento.values()) {
			if(cod.equals(e.cod)) {
				return e;
			}
		}
		throw new IllegalArgumentException("Não foi encontrado o código informado");
	}
}
