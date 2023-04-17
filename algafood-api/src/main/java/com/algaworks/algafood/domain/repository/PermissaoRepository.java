package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao>  verificarPermissao();
	
	Permissao buscarPermissao(Long id);
	
	Permissao concederPermissao(Permissao permissao);
	
	void removerPermissao (Permissao permissao);
	
}
