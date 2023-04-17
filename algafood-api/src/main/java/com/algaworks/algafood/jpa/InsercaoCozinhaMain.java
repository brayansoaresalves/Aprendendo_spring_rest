package com.algaworks.algafood.jpa;

import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class InsercaoCozinhaMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository repository = contexto.getBean(CozinhaRepository.class);
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("BRAZILEIRA");
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("JAPONEZA");
		
		cozinha  = repository.salvar(cozinha);
		cozinha1 = repository.salvar(cozinha1);
		
		System.out.printf("%d - %s \n", cozinha.getId(), cozinha.getNome());
		System.out.printf("%d - %s \n", cozinha1.getId(), cozinha1.getNome());
	}

}
