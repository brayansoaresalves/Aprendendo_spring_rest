package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public PedidoNaoEncontradoException(Long pedidoId) {
		this(String.format("O pedido de código %d não foi emitido ou não existe!!", pedidoId));
	}

}
