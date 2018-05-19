INSERT INTO usuarios(username,password,ativo)
VALUES ('steffan','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', true);
INSERT INTO usuarios(username,password,ativo)
VALUES ('ana','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', true); --senha: password

INSERT INTO autorizacoes (username, autorizacao)
VALUES ('steffan', 'ROLE_SINDICO');
INSERT INTO autorizacoes (username, autorizacao)
VALUES ('ana', 'ROLE_MORADOR');