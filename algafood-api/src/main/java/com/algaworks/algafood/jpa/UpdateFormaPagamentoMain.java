package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Forma_Pagamento;
import com.algaworks.algafood.domain.repository.Forma_PagamentoRepository;

public class UpdateFormaPagamentoMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		Forma_PagamentoRepository repository = contexto.getBean(Forma_PagamentoRepository.class);
		
		Forma_Pagamento pagamento = new Forma_Pagamento();
		pagamento.setId(4L);
		pagamento.setDescricao("CARTÃO DE CREDITO - PARCELAMENTO OU A VISTA");
		
		pagamento = repository.efetuarTransacao(pagamento);
		
		System.out.println(pagamento);
		
		
	}
	
	

}
