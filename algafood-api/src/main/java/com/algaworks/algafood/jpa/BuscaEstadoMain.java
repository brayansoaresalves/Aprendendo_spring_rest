package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class BuscaEstadoMain {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		EstadoRepository repositorio = contexto.getBean(EstadoRepository.class);
		
		List<Estado> estados = repositorio.consultarEstado();
		
		for (Estado estado : estados) {
			System.out.println(estado);
		}
	}

}
