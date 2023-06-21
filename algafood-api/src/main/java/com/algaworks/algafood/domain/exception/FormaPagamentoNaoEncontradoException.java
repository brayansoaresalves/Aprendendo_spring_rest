package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradoException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public FormaPagamentoNaoEncontradoException (Long pagamentoId) {
		this(String.format("Não existe o pagamento com o codigo %d", pagamentoId));
	}
	
	

}
