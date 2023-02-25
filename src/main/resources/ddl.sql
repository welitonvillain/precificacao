create table auditoria_precos (id raw(16) not null, usuario varchar2(200 char) not null, ip varchar2(20 char) not null, nome varchar2(200 char) not null, conteudo blob not null, resultado clob, sucesso smallint, dh_inicial timestamp not null, dh_final timestamp, primary key (id));
create table codigo_lista_precos (id raw(16) not null, tipo varchar2(100 char) not null, nome varchar2(100 char) not null, loja number(10,0) not null, codigo varchar2(100 char) not null, primary key (id));
alter table codigo_lista_precos add constraint codigo_lista_idx1 unique (tipo, nome, loja);
alter table codigo_lista_precos add constraint codigo_lista_idx2 unique (codigo);
