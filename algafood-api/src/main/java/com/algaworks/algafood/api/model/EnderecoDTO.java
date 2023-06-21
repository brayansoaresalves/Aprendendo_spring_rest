package com.algaworks.algafood.api.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.algaworks.algafood.domain.model.Cidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
	
	private String cep;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;
	
	private CidadeResumoDTO cidade;

}
