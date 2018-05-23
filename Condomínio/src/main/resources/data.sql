INSERT INTO usuarios(username,password,ativo,nome,sobrenome,email)
VALUES ('steffan','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', true,'steffan','martins alves','steffan@ufu.br');
INSERT INTO usuarios(username,password,ativo,nome,sobrenome,email)
VALUES ('ana','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', true,'ana paula','rodrigues','teste@ufu.com');

INSERT INTO autorizacoes (username, autorizacao)
VALUES ('steffan', 'ROLE_SINDICO');
INSERT INTO autorizacoes (username, autorizacao)
VALUES ('ana', 'ROLE_MORADOR');