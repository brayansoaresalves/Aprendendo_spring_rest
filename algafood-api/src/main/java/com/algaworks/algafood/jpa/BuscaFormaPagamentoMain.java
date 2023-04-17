package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Forma_Pagamento;
import com.algaworks.algafood.domain.repository.Forma_PagamentoRepository;

public class BuscaFormaPagamentoMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		Forma_PagamentoRepository repositorio = contexto.getBean(Forma_PagamentoRepository.class);
		
		
		Forma_Pagamento pagamento = repositorio.pagamento(1L);
		System.out.println(pagamento);
		
	}

}
