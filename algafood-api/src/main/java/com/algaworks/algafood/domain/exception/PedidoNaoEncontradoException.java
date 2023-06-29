package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("O pedido de código %s não foi emitido ou não existe!!", codigoPedido));
	}

}
