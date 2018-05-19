drop table autorizacoes;
drop table persistent_logins;
drop table usuarios;

create table usuarios(
    username varchar(50) not null primary key,
    password varchar(100) not null,
    ativo boolean not null
);

create table autorizacoes (
    username varchar(50) not null,
    autorizacao varchar(50) not null,
    constraint fk_autorizacoes_usuarios foreign key(username) references usuarios(username)
);
create unique index ix_auth_username on autorizacoes (username,autorizacao);

create table persistent_logins (
    username varchar(64) not null,
    series varchar(64) primary key,
    token varchar(64) not null,
    last_used timestamp not null
);