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

CREATE TABLE Usuarios (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  ativo BOOL NOT NULL DEFAULT true,
  nome VARCHAR(50) NOT NULL,
  sobrenome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  idCondominio BIGINT UNSIGNED NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(idCondominio)
    REFERENCES Condominios(idCondominio)
      ON DELETE SET NULL
      ON UPDATE CASCADE
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

CREATE TABLE Blocos (
  idBloco BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  idCondominio BIGINT UNSIGNED NOT NULL,
  sigla VARCHAR(3) NOT NULL,
  descricao VARCHAR(30) NULL,
  PRIMARY KEY(idBloco),
  FOREIGN KEY(idCondominio)
    REFERENCES Condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Moradias (
  idMoradia INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  sigla VARCHAR(10) NOT NULL,
  tipo VARCHAR(2) NOT NULL,
  area FLOAT NULL,
  fracaoIdeal FLOAT NULL,
  matricula VARCHAR(30) NULL,
  vagas INTEGER UNSIGNED NULL,
  idBloco BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idMoradia),
  FOREIGN KEY(idBloco)
    REFERENCES Blocos(idBloco)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);