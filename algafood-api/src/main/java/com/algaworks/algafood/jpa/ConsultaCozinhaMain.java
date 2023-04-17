package com.algaworks.algafood.jpa;

import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.infrastructure.repository.CozinhaRepositoryimpl;

public class ConsultaCozinhaMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		CozinhaRepository repository = contexto.getBean(CozinhaRepository.class);
		
		List<Cozinha> cozinhas = repository.listar();
		
		for (Cozinha cozinha : cozinhas) {
			System.out.println(cozinha.getNome());
		}
	}

}
