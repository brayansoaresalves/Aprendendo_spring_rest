package com.algaworks.algafood.jpa;

import org.hibernate.id.Configurable;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Forma_Pagamento;
import com.algaworks.algafood.domain.repository.Forma_PagamentoRepository;

public class InsercaoFormaPagamentoMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		Forma_PagamentoRepository repository = contexto.getBean(Forma_PagamentoRepository.class);
		
		Forma_Pagamento pagamento = new Forma_Pagamento();
		pagamento.setDescricao("CHEQUE");
		Forma_Pagamento pagamento2 = new Forma_Pagamento();
		pagamento2.setDescricao("PIX");
		
		pagamento = repository.efetuarTransacao(pagamento);
		pagamento2 = repository.efetuarTransacao(pagamento2);
		
		System.out.println(pagamento);
		System.out.println(pagamento2);
		
	}

}
