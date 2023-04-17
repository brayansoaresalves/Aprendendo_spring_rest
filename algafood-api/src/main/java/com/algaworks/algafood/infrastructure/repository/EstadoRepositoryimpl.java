package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class EstadoRepositoryimpl implements EstadoRepository{
	
	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Estado> consultarEstado() {
		// TODO Auto-generated method stub
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Estado buscarEstadoPorCodigo(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Estado.class, id);
	}

	@Override
	@Transactional
	public Estado cadastrarEstado(Estado estado) {
		// TODO Auto-generated method stub
		return manager.merge(estado);
	}

	@Override
	@Transactional
	public void excluirEstado(Long id) {
		// TODO Auto-generated method stub
		Estado estado = buscarEstadoPorCodigo(id);
		
		if (estado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(estado);
		
	}
	
	

}
