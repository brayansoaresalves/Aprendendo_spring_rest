package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String msg) {
		super(msg);

	}
	
	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe cadastro de estado com o codigo %d", cozinhaId));
	}

}
