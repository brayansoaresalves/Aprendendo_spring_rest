package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cidades;

public interface CidadeRepository {
	
	List<Cidades> ConsultarCidade();
	
	Cidades buscarCidade(Long id);
	
	Cidades cadastrarCidade(Cidades cidade);
	
	void removeCidade(Long id);

}
