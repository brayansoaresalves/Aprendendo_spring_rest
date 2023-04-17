package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Forma_Pagamento;
import com.algaworks.algafood.domain.repository.Forma_PagamentoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class Forma_PagamentoRepositoryimpl implements Forma_PagamentoRepository{
	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public List<Forma_Pagamento> comprovantes() {
		// TODO Auto-generated method stub
		return manager.createQuery("from Forma_Pagamento", Forma_Pagamento.class).getResultList();
	}

	@Override
	public Forma_Pagamento pagamento(Long codigo) {
		// TODO Auto-generated method stub
		return manager.find(Forma_Pagamento.class, codigo);
	}

	@Override
	@Transactional
	public Forma_Pagamento efetuarTransacao(Forma_Pagamento pagamento) {
		// TODO Auto-generated method stub
		return manager.merge(pagamento);
	}

	@Override
	@Transactional
	public void reestituicao(Forma_Pagamento pagamento) {
		// TODO Auto-generated method stub
		pagamento = pagamento(pagamento.getId());
		manager.remove(pagamento);
	}

}
