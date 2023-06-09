package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public ProdutoNaoEncontradoException (Long produtoId, Long restauranteId) {
		this(String.format("Não existe um cadastro de produto com o código %d para o"
				+ "restaurante de código %d.", produtoId, restauranteId));
	}
	
	public ProdutoNaoEncontradoException (Long produtoId) {
		this(String.format("Não existe um cadastro de produto com o código %d.", produtoId));
	}

}
