package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entidade não encontrada")
public class EntidadeNaoEncontradaException extends ResponseStatusException{

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(HttpStatusCode status, String reason) {
		super(status, reason);
		// TODO Auto-generated constructor stub
	}

	public EntidadeNaoEncontradaException(String msg) {
		this(HttpStatus.NOT_FOUND, msg);
	}

}
