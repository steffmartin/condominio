CREATE TABLE Usuarios (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  ativo BOOL NOT NULL DEFAULT true,
  nome VARCHAR(50) NOT NULL,
  sobrenome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY(id),
  UNIQUE INDEX Usuarios_unique(username)
);

CREATE TABLE Autorizacoes (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  autorizacao VARCHAR(50) NOT NULL,
  PRIMARY KEY(id),
  UNIQUE INDEX Autorizacoes_unique(username, autorizacao)
);

CREATE TABLE persistent_logins (
  series VARCHAR(64) NOT NULL,
  username VARCHAR(50) NOT NULL,
  token VARCHAR(64) NOT NULL,
  last_used TIMESTAMP NOT NULL,
  PRIMARY KEY(series)
);