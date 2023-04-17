package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class PermissaoRepositoryimpl implements PermissaoRepository{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Permissao> verificarPermissao() {
		// TODO Auto-generated method stub
		return entityManager.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	public Permissao buscarPermissao(Long id) {
		// TODO Auto-generated method stub
		return entityManager.find(Permissao.class, id);
	}

	@Override
	@Transactional
	public Permissao concederPermissao(Permissao permissao) {
		// TODO Auto-generated method stub
		return entityManager.merge(permissao);
	}

	@Override
	@Transactional
	public void removerPermissao(Permissao permissao) {
		// TODO Auto-generated method stub
		permissao = buscarPermissao(permissao.getId());
		entityManager.remove(permissao);
		
	}

}
