package com.algaworks.algafood.jpa;

import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class ConsultaRestauranteMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		RestauranteRepository repository = contexto.getBean(RestauranteRepository.class);
		
		List<Restaurante> restaurantes = repository.listar();
		
		for (Restaurante restaurante : restaurantes) {
			System.out.printf("%d - %s %f %s \n", restaurante.getId(), restaurante.getNome(), 
					restaurante.getTaxaFrete(), restaurante.getCozinha().getNome());
		}
	}

}
