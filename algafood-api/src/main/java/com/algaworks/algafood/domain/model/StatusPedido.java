package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
	
	CRIADO("criado"),
	CONFIRMADO("confirmado", CRIADO),
	ENTREGUE("entregue", CONFIRMADO),
	CANCELADO("cancelado", CRIADO);
	
	private String descricao;
	private List<StatusPedido> statusAnteriores;
	
	private StatusPedido(String descricao, StatusPedido...statusAnteriores) {
		// TODO Auto-generated constructor stub
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}

}
