package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Forma_Pagamento;

public interface Forma_PagamentoRepository {
	
	List<Forma_Pagamento> comprovantes();
	Forma_Pagamento pagamento(Long codigo);
	Forma_Pagamento efetuarTransacao(Forma_Pagamento pagamento);
	void reestituicao(Forma_Pagamento pagamento);
	

}
