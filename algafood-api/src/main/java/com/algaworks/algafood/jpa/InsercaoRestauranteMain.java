package com.algaworks.algafood.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class InsercaoRestauranteMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		
		RestauranteRepository repository = contexto.getBean(RestauranteRepository.class);
		
		Restaurante rest = new Restaurante();
		rest.setNome("TIÃO CARREIRO ESPETO E PORÇÕES");
		rest.setTaxaFrete(new BigDecimal(0.15));
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome("PEDREGULHO");
		restaurante.setTaxaFrete(new BigDecimal(0.25));
		
		rest = repository.salvar(rest);
		restaurante = repository.salvar(restaurante);
		
		System.out.println(rest);
		System.out.println(restaurante);
	}

}
