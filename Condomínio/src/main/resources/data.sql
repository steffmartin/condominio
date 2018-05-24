INSERT INTO usuarios(username,password,ativo,nome,sobrenome,email)
VALUES ('steffan','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', true,'steffan','martins alves','steffan@ufu.br');
INSERT INTO usuarios(username,password,ativo,nome,sobrenome,email)
VALUES ('ana','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', true,'ana paula','rodrigues','teste@ufu.com');

INSERT INTO autorizacoes (id_usuario, autorizacao)
VALUES (1, 'ROLE_SINDICO');
INSERT INTO autorizacoes (id_usuario, autorizacao)
VALUES (2, 'ROLE_MORADOR');