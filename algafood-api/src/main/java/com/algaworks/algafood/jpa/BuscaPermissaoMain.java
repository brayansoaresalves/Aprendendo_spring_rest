package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

public class BuscaPermissaoMain {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		PermissaoRepository permissaoRepository = contexto.getBean(PermissaoRepository.class);
		
		List<Permissao> permissoes = permissaoRepository.verificarPermissao();
		
		for (Permissao permissao : permissoes) {
			System.out.println(permissao);
		}
	}

}
