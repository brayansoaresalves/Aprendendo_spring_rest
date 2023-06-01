package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	DADOS_INVALIDOS("/dadis-invalidos", "Dados invalidos"),
	ERRO_DO_SISTEMA("/erro-do-sistena", "Erro do Sistemna"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parametro invalido"),
	MENSAGEM_ENCOMPREENSIVEL("/mensagem-encompreensivel", "Mensagem Encompreensivel"),
	RECURSO_NAO_ENCONTRADO("/entidade-nao-encontrada", "Entidade Não Encontrada"),
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
