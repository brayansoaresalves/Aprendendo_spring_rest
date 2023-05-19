package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	MENSAGEM_ENCOMPREENSIVEL("/mensagem-encompreensivel", "Mensagem Encompreensivel"),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade Não Encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade está em Uso"), 
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	PROPRIEDADE_INEXISTENTE("/propriedade-inexistente", "Propriedade não existe");    
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
	

}
