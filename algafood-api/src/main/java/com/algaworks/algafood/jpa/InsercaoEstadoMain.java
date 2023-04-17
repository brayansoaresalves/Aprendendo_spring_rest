package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class InsercaoEstadoMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		EstadoRepository estadoRepository = contexto.getBean(EstadoRepository.class);
		
		Estado estado = new Estado();
		Estado estado2 = new Estado();
		
		estado.setNome("RIO DE JANEIRO");
		estado2.setNome("DISTRITO FEDERAL");
		
		estado = estadoRepository.cadastrarEstado(estado);
		estado2 = estadoRepository.cadastrarEstado(estado2);
		
		System.out.println(estado);
		System.out.println(estado2);
	}

}
