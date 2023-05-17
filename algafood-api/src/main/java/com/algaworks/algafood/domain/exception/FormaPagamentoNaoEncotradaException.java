package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncotradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncotradaException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public FormaPagamentoNaoEncotradaException(Long formaId) {
		this(String.format("Não existe uma forma de pagamento com o código %d", formaId));
	}

}
