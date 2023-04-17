package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.repository.CidadeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CidadeRepositoryimpl implements CidadeRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cidades> ConsultarCidade() {
		// TODO Auto-generated method stub
		return manager.createQuery("from Cidades", Cidades.class).getResultList();
	}

	@Override
	public Cidades buscarCidade(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Cidades.class, id);
	}

	@Override
	@Transactional
	public Cidades cadastrarCidade(Cidades cidade) {
		// TODO Auto-generated method stub
		return manager.merge(cidade);
	}

	@Override
	@Transactional
	public void removeCidade(Long id) {
		// TODO Auto-generated method stub
		Cidades cidade = buscarCidade(id);
		
		if (cidade == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(cidade);
		
	}

}
