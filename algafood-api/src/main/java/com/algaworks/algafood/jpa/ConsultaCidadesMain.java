package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.repository.CidadeRepository;

public class ConsultaCidadesMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		CidadeRepository repositorio = contexto.getBean(CidadeRepository.class);
		
		Cidades cidade = repositorio.buscarCidade(2L);
		 System.out.println(cidade);
	}

}
