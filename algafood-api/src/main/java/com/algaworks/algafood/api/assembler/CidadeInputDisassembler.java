package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidades;

@Component
public class CidadeInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidades ToDomainObject(CidadeInput cidadeInput){
		return modelMapper.map(cidadeInput, Cidades.class);
	}
	
	public void copyToDomainObject(CidadeInput cidadeInput, Cidades cidade) {
		modelMapper.map(cidadeInput, cidade);
	}
}
