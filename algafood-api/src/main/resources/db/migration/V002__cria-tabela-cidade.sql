create table cidades (
	id bigint AUTO_INCREMENT, 
    nome_cidade varchar(80) not null, 
    nome_estado varchar(80) not null,
    primary key(id)
)engine=InnoDB DEFAULT CHARSET=UTF8;