package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository {
	
	List<Estado> consultarEstado();
	
	Estado buscarEstadoPorCodigo(Long id);
	
	Estado cadastrarEstado(Estado estado);
	
	void excluirEstado(Long id);
	

}
