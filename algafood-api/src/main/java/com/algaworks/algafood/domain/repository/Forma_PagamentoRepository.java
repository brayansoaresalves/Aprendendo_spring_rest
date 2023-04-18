package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Forma_Pagamento;

@Repository
public interface Forma_PagamentoRepository extends JpaRepository<Forma_Pagamento, Long>{
	

}
