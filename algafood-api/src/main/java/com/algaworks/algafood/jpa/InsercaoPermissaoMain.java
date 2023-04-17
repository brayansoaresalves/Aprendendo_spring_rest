package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

public class InsercaoPermissaoMain {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		PermissaoRepository repository = contexto.getBean(PermissaoRepository.class);
		
		Permissao permissao = new Permissao();
		Permissao per = new Permissao();
		
		permissao.setNome("comnpras");
		permissao.setDescricao("Pedidos de Compra");
		
		per.setNome("pedido");
		per.setDescricao("Emissão de Pedidos");
		
		permissao = repository.concederPermissao(permissao);
		per = repository.concederPermissao(per);
		
		System.out.println(permissao);
		System.out.println(per);
	}

}
