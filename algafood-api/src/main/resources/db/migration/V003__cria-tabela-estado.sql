insert into cidades (nome_cidade, nome_estado) values ('UBERLÂNDIA', 'MINAS GERAIS');
insert into cidades (nome_cidade, nome_estado) values ('BELO HORIZONTE', 'MINAS GERAIS');
insert into cidades (nome_cidade, nome_estado) values ('SÃO PAULO', 'SÃO PAULO');
insert into cidades (nome_cidade, nome_estado) values ('RECIFE', 'PERNANBUCO');


create table estado (
	id bigint AUTO_INCREMENT not null, 
    nome varchar(80) not null, 
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into estado(nome) select distinct nome_estado from cidades;

alter table cidades add column estado_id bigint not null;

update cidades c set c.estado_id = (select e.id from estado e where e.nome = c.nome_estado);

alter table cidades add constraint fk_cidades_estado foreign key(estado_id) references estado(id);

alter table cidades drop column nome_estado;

alter table cidades change nome_cidade nome varchar(80) not null;