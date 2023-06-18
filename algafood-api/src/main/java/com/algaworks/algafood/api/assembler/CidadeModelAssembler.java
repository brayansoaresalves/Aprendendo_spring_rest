package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidades;

@Component
public class CidadeModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeDTO toModel(Cidades cidade) {
		return modelMapper.map(cidade, CidadeDTO.class);
	}
	
	public List<CidadeDTO> toCollectionModel(List<Cidades> cidades){
		return cidades.stream().map(cidade -> toModel(cidade)).collect(Collectors.toList());
	}

}
