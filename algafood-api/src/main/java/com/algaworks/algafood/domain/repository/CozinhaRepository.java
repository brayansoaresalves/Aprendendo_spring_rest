package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> listar();
	Cozinha buscar(Long id);
	
	List<Cozinha> consultarPorNome(String nome);
	
	Cozinha salvar(Cozinha cozinha);
	void remover(Long id);
}
