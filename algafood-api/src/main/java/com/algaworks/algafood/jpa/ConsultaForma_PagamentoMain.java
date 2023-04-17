package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Forma_Pagamento;
import com.algaworks.algafood.domain.repository.Forma_PagamentoRepository;

public class ConsultaForma_PagamentoMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		Forma_PagamentoRepository repositorio = contexto.getBean(Forma_PagamentoRepository.class);
		
		List<Forma_Pagamento> pagamentos = repositorio.comprovantes();
		
		for (Forma_Pagamento pagamento : pagamentos) {
			System.out.println(pagamento.getDescricao());
		}
		
	}

}
