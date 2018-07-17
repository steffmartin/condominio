CREATE TABLE Usuarios (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  ativo BOOL NOT NULL DEFAULT true,
  nome VARCHAR(50) NOT NULL,
  sobrenome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY(id),
  UNIQUE INDEX Usuarios_unique_username(username)
);

CREATE TABLE Autorizacoes (
  id_usuario BIGINT UNSIGNED NOT NULL,
  autorizacao VARCHAR(50) NOT NULL,
  PRIMARY KEY(id_usuario, autorizacao),
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

CREATE TABLE Condominios (
  idCondominio BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  razaoSocial VARCHAR(100) NOT NULL,
  cnpj VARCHAR(14) NULL,
  ie VARCHAR(14) NULL,
  im VARCHAR(30) NULL,
  email VARCHAR(100) NULL,
  telefone VARCHAR(10) NULL,
  celular VARCHAR(11) NULL,
  endereco VARCHAR(100) NOT NULL,
  numeroEnd VARCHAR(6) NOT NULL,
  complementoEnd VARCHAR(30) NULL,
  bairro VARCHAR(30) NOT NULL,
  cidade VARCHAR(30) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  cep VARCHAR(8) NOT NULL,
  PRIMARY KEY(idCondominio)
);