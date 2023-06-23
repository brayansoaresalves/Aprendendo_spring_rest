alter table restaurante add abertura TINYINT(1) not null;
update restaurante set abertura = true;