package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

public class UpdatePermissaoMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		PermissaoRepository permissaoRepository = contexto.getBean(PermissaoRepository.class);
		
		Permissao permissao = new Permissao();
		permissao.setId(3L);
		permissao.setNome("excluir");
		permissao.setDescricao("Permissão para exclusão concedida");
		
		permissao = permissaoRepository.concederPermissao(permissao);
		System.out.println(permissao);
	}

}
