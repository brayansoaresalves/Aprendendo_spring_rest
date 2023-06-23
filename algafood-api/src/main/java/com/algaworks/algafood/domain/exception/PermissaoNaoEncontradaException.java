package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontradaException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	 
	public PermissaoNaoEncontradaException (Long permissaoId) {
		this(String.format("A permissão de código %d não existe", permissaoId));
	}

}
