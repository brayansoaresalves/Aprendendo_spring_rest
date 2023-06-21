package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public GrupoNaoEncontradoException(Long id) {
		this(String.format("O grupo de Código %d não está cadastrado", id));
	}

}
