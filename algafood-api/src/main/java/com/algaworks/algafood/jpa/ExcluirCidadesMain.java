package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.repository.CidadeRepository;

public class ExcluirCidadesMain {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		CidadeRepository cidadeRepository = contexto.getBean(CidadeRepository.class);
		
		Cidades cidade = new Cidades();
		cidade.setId(3L);
		cidadeRepository.removeCidade(cidade.getId());
	}

}
