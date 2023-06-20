alter table restaurante add ativo TINYINT(1) not null;
update restaurante set ativo = true;