package com.algaworks.algafood.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;

import jakarta.persistence.EntityManager;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> 
implements CustomJpaRepository<T, ID>{
	
	private EntityManager manager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		// TODO Auto-generated constructor stub
		this.manager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		// TODO Auto-generated method stub
		
		var jpql = "from " + getDomainClass().getName();
		
		T entity = manager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();
		
		return Optional.ofNullable(entity);
	}

	

}