create table forma_pagamento (
id bigint not null auto_increment, 
descricao varchar(60), 
primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8;

create table grupo (
id bigint not null auto_increment, 
nome varchar(80) not null,
 primary key (id)
 ) engine=InnoDB DEFAULT CHARSET=utf8;
 
create table grupo_permissao (
grupo_id bigint not null,
 permissao_id bigint not null
 ) engine=InnoDB DEFAULT CHARSET=utf8;
 
create table permissao (
id bigint not null auto_increment,
 descricao varchar(80),
  nome varchar(120), 
  primary key (id)
  ) engine=InnoDB DEFAULT CHARSET=utf8;
  
create table produto (
id bigint not null auto_increment,
 ativo bit not null,
  descricao varchar(150) not null,
   nome varchar(150) not null, 
   preco decimal(15,2) not null, 
   restaurante_id bigint not null,
    primary key (id)
    ) engine=InnoDB DEFAULT CHARSET=utf8;
    
create table restaurante (
id bigint not null auto_increment,
 data_atualizacao datetime not null,
  data_cadastro datetime not null,
   endereco_bairro varchar(50), 
   endereco_cep varchar(15),
    endereco_complemento varchar(80), 
    endereco_logradouro varchar(60), 
    endereco_numero varchar(10), 
    nome varchar(50) not null,
     taxa_frete decimal(15,2) not null,
      cozinha_codigo bigint not null,
       endereco_cidade_id bigint,
        primary key (id)
        ) engine=InnoDB DEFAULT CHARSET=utf8;
        
create table restaurante_forma_pagamento (
restaurante_id bigint not null,
 forma_pagamento_id bigint not null
 ) engine=InnoDB DEFAULT CHARSET=utf8;
 
create table usuario (
id bigint not null auto_increment,
 data_cadastro datetime not null,
  email varchar(100) not null,
   nome varchar(80) not null,
    senha varchar(20) not null,
     primary key (id)
     ) engine=InnoDB DEFAULT CHARSET=utf8;
     
create table usuario_grupo (
usuario_id bigint not null,
 grupo_id bigint not null
 ) engine=InnoDB DEFAULT CHARSET=utf8;
 

alter table grupo_permissao add constraint fk_grupo_permissao_permissao foreign key (permissao_id) references permissao (id);

alter table grupo_permissao add constraint fk_grupo_permissao_grupo foreign key (grupo_id) references grupo (id);

alter table produto add constraint fk_produto_restaurante foreign key (restaurante_id) references restaurante (id);

alter table restaurante add constraint fk_restaurante_cozinha foreign key (cozinha_codigo) references cozinha (id);

alter table restaurante add constraint fk_restaurante_endereco_cidade_id foreign key (endereco_cidade_id) references cidades (id);

alter table restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento_fp foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento_restaurante foreign key (restaurante_id) references restaurante (id);

alter table usuario_grupo add constraint fk_usuario_grupo_gp foreign key (grupo_id) references grupo (id);

alter table usuario_grupo add constraint fk_usuario_grupo_user foreign key (usuario_id) references usuario (id);
