insert into cozinha(nome) values ('TAILANDEZA');
insert into cozinha(nome) values ('INDIANA');

insert into restaurante (nome, taxa_frete, cozinha_codigo) values ('YUKIHIRA', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_codigo) values ('SHINOS', 15, 2);

insert into forma_pagamento (descricao) values ('A VISTA');
insert into forma_pagamento (descricao) values ('DINHEIRO');
insert into forma_pagamento (descricao) values ('CARTÃO DE DEBITO');
insert into forma_pagamento (descricao) values ('CARTÂO DE CREDITO');

insert into permissao (nome, descricao) values ('cadastro', 'MENU CADASTRO');
insert into permissao (nome, descricao) values ('relatorios', 'ACESSO A RELATORIO DE ESTOQUE');
insert into permissao (nome, descricao) values ('administrador', 'ACESSO DE ADMINISTRADOR');

insert into estado (nome) values ('GOIAS');
insert into estado (nome) values ('MINAS GERAIS');
insert into estado (nome) values ('SÃO PAULO');

insert into cidades (nome, estado_id) values ('ITUMBIARA', 1);
insert into cidades (nome, estado_id) values ('ARAPORÃ', 2);
insert into cidades (nome, estado_id) values ('SÃO PAULO', 3);
insert into cidades (nome, estado_id) values ('CACHOEIRA DOURADA', 1);
insert into cidades (nome, estado_id) values ('UBERLÃNDIA', 2);
insert into cidades (nome, estado_id) values ('MATÃO', 3);