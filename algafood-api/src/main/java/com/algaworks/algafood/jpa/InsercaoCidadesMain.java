package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidades;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class InsercaoCidadesMain {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		ConfigurableApplicationContext contexto2 = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);
		CidadeRepository cidadeRepository = contexto.getBean(CidadeRepository.class);
		EstadoRepository estadoRepository = contexto2.getBean(EstadoRepository.class);
		
		Cidades cidades = new Cidades();
		Cidades cid2 = new Cidades();
		
		Estado estado = new Estado();
		estado = estadoRepository.buscarEstadoPorCodigo(1L);
		
		
		cidades.setNome("PANAMA");
		cidades.setEstado(estado);
		
		cidades =cidadeRepository.cadastrarCidade(cidades);
		
		System.out.println(cidades);
	}
}
