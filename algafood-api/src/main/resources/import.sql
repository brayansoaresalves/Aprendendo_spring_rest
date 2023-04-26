insert into cozinha(nome) values ('TAILANDEZA');
insert into cozinha(nome) values ('INDIANA');

insert into estado (nome) values ('GOIAS');
insert into estado (nome) values ('MINAS GERAIS');
insert into estado (nome) values ('SÃO PAULO');

insert into cidades (nome, estado_id) values ('ITUMBIARA', 1);
insert into cidades (nome, estado_id) values ('ARAPORÃ', 2);
insert into cidades (nome, estado_id) values ('SÃO PAULO', 3);
insert into cidades (nome, estado_id) values ('CACHOEIRA DOURADA', 1);
insert into cidades (nome, estado_id) values ('UBERLÃNDIA', 2);
insert into cidades (nome, estado_id) values ('MATÃO', 3);

insert into restaurante (nome, taxa_frete, cozinha_codigo, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (nome, taxa_frete, cozinha_codigo) values ('SHINOS', 15, 2);
insert into restaurante (nome, taxa_frete, cozinha_codigo) values ('TADOKORO', 0, 2);

insert into forma_pagamento (descricao) values ('A VISTA');
insert into forma_pagamento (descricao) values ('DINHEIRO');
insert into forma_pagamento (descricao) values ('CARTÃO DE DEBITO');
insert into forma_pagamento (descricao) values ('CARTÂO DE CREDITO');

insert into permissao (nome, descricao) values ('cadastro', 'MENU CADASTRO');
insert into permissao (nome, descricao) values ('relatorios', 'ACESSO A RELATORIO DE ESTOQUE');
insert into permissao (nome, descricao) values ('administrador', 'ACESSO DE ADMINISTRADOR');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,3), (3,2), (3,3);