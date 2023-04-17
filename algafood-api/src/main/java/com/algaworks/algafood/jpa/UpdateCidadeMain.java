package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class UpdateCidadeMain {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		ConfigurableApplicationContext contexto2 = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		EstadoRepository estadoRepository = contexto.getBean(EstadoRepository.class);
		CidadeRepository cidadeRepository = contexto2.getBean(CidadeRepository.class);
		
		Estado estado = new Estado();
		Cidades cidade = new Cidades();
		
		estado = estadoRepository.buscarEstadoPorCodigo(2L);
		cidade.setId(2L);
		cidade.setNome("MINAS GERAIS");
		cidade.setEstado(estado);
		
		cidade = cidadeRepository.cadastrarCidade(cidade);
		
		System.out.println(cidade);
	}

}
