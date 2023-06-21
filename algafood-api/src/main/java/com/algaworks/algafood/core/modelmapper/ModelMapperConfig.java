package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.EnderecoDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		/* fazer isso para quando quisermaos mapear um nome que o mapper nao consegue fazer por si proprio
		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
		.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);*/
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome()
				, (enderecoModelDest, valor) -> enderecoModelDest.getCidade().setEstado(valor));

		return modelMapper;
	}

}
