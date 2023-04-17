package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class UpdateEstadoMain {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		EstadoRepository estadoRepository = contexto.getBean(EstadoRepository.class);
		
		Estado estado = new Estado();
		estado.setId(2l);
		estado.setNome("Minas Gerais");
		
		estado = estadoRepository.cadastrarEstado(estado);
		System.out.println(estado);
	}

}
