set foreign_key_checks = 0;

delete from cozinha;
delete from cidades;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 0;

alter table cozinha auto_increment=1;
alter table cidades auto_increment=1;
alter table estado auto_increment=1;
alter table forma_pagamento auto_increment=1;
alter table grupo auto_increment=1;
alter table permissao auto_increment=1;
alter table produto auto_increment=1;
alter table restaurante auto_increment=1;
alter table usuario auto_increment=1;

insert into cozinha(nome) values ('TAILANDEZA');
insert into cozinha(nome) values ('INDIANA');
insert into cozinha(nome) values ('BRASILEIRA');
insert into cozinha(nome) values ('ARGENTINA');
insert into cozinha(nome) values ('JAPONEZA');

insert into estado (nome) values ('GOIAS');
insert into estado (nome) values ('MINAS GERAIS');
insert into estado (nome) values ('SÃO PAULO');

insert into cidades (nome, estado_id) values ('ITUMBIARA', 1);
insert into cidades (nome, estado_id) values ('ARAPORÃ', 2);
insert into cidades (nome, estado_id) values ('SÃO PAULO', 3);
insert into cidades (nome, estado_id) values ('CACHOEIRA DOURADA', 1);
insert into cidades (nome, estado_id) values ('UBERLÃNDIA', 2);
insert into cidades (nome, estado_id) values ('MATÃO', 3);

insert into restaurante (nome, taxa_frete, cozinha_codigo, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Thai Gourmet', 4, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (nome, taxa_frete, cozinha_codigo, data_cadastro, data_atualizacao) values ('Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_codigo, data_cadastro, data_atualizacao) values ('Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_codigo, data_cadastro, data_atualizacao) values ('YUKIHIRA', 6.12, 4, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_codigo, data_cadastro, data_atualizacao) values ('TADOKORO', 11.0, 5, utc_timestamp, utc_timestamp);

insert into forma_pagamento (descricao) values ('A VISTA');
insert into forma_pagamento (descricao) values ('DINHEIRO');
insert into forma_pagamento (descricao) values ('CARTÃO DE DEBITO');
insert into forma_pagamento (descricao) values ('CARTÂO DE CREDITO');

insert into permissao (nome, descricao) values ('cadastro', 'MENU CADASTRO');
insert into permissao (nome, descricao) values ('relatorios', 'ACESSO A RELATORIO DE ESTOQUE');
insert into permissao (nome, descricao) values ('administrador', 'ACESSO DE ADMINISTRADOR');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,3), (3,2), (3,3), (4, 1), (4, 2), (5, 1), (5, 2);


insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('PIZZA', 'PIZZA A MODA', 35.00, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('MACARRONADA', 'MACARRONADA COM SALSICHA', 30, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

