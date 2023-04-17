package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CozinhaRepositoryimpl implements CozinhaRepository{
	
	@PersistenceContext
	EntityManager manager;

	@Override
	public List<Cozinha> listar() {
		// TODO Auto-generated method stub
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	public Cozinha buscar(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Cozinha.class, id);
	}

	@Override
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		// TODO Auto-generated method stub
		return manager.merge(cozinha);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		// TODO Auto-generated method stub
		Cozinha cozinha = buscar(id);
		
		if (cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(cozinha);
		
	}

	@Override
	public List<Cozinha> consultarPorNome(String nome) {
		// TODO Auto-generated method stub
		return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class).setParameter("nome", "%" + nome + "%")
				.getResultList();
	}

}
