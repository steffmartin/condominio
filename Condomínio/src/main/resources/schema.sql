CREATE TABLE Usuarios (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  ativo BOOL NOT NULL DEFAULT true,
  nome VARCHAR(50) NOT NULL,
  sobrenome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY(id),
  UNIQUE INDEX Usuarios_unique_username(username),
  UNIQUE INDEX Usuarios_unique_email(email)
);

CREATE TABLE Autorizacoes (
  id_usuario BIGINT UNSIGNED NOT NULL,
  autorizacao VARCHAR(50) NOT NULL,
  PRIMARY KEY(id_usuario),
  UNIQUE INDEX Autorizacoes_unique_id_autorizacao(autorizacao),
  FOREIGN KEY(id_usuario)
    REFERENCES Usuarios(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE persistent_logins (
  series VARCHAR(64) NOT NULL,
  username VARCHAR(50) NOT NULL,
  token VARCHAR(64) NOT NULL,
  last_used TIMESTAMP NOT NULL,
  PRIMARY KEY(series)
);