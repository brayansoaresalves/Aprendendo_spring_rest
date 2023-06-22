package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cidades;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidades, Long> {
	

}
