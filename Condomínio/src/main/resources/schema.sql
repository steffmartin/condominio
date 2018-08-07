CREATE TABLE Condominios (
  idCondominio BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  razaoSocial VARCHAR(100) NULL,
  cnpj VARCHAR(14) NULL,
  ie VARCHAR(14) NULL,
  im VARCHAR(30) NULL,
  email VARCHAR(100) NULL,
  telefone VARCHAR(10) NULL,
  celular VARCHAR(11) NULL,
  endereco VARCHAR(100) NULL,
  numeroEnd VARCHAR(6) NULL,
  complementoEnd VARCHAR(30) NULL,
  bairro VARCHAR(30) NULL,
  cidade VARCHAR(30) NULL,
  estado VARCHAR(2) NULL,
  cep VARCHAR(8) NULL,
  PRIMARY KEY(idCondominio)
);

CREATE TABLE Usuarios (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NULL,
  password VARCHAR(100) NULL,
  ativo BOOL NULL,
  nome VARCHAR(50) NULL,
  sobrenome VARCHAR(100) NULL,
  email VARCHAR(100) NULL,
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
  username VARCHAR(50) NULL,
  token VARCHAR(64) NULL,
  last_used TIMESTAMP NULL,
  PRIMARY KEY(series),
    FOREIGN KEY(username)
    REFERENCES Usuarios(username)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
);

CREATE TABLE Blocos (
  idBloco BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  sigla VARCHAR(3) NULL,
  descricao VARCHAR(30) NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idBloco),
  FOREIGN KEY(idCondominio)
    REFERENCES Condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Moradias (
  idMoradia BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  sigla VARCHAR(10) NULL,
  tipo VARCHAR(2) NULL,
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

CREATE TABLE Pessoas (
  idPessoa BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NULL,
  email VARCHAR(100) NULL,
  telefone VARCHAR(10) NULL,
  celular VARCHAR(11) NULL,
  endereco VARCHAR(100) NULL,
  numeroEnd VARCHAR(6) NULL,
  complementoEnd VARCHAR(30) NULL,
  bairro VARCHAR(30) NULL,
  cidade VARCHAR(30) NULL,
  estado VARCHAR(2) NULL,
  cep VARCHAR(8) NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idPessoa),
  FOREIGN KEY(idCondominio)
    REFERENCES Condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE PessoasFisicas (
  idPessoa BIGINT UNSIGNED NOT NULL,
  sobrenome VARCHAR(100) NULL,
  cpf VARCHAR(11) NULL,
  rg VARCHAR(14) NULL,
  nascimento DATE NULL,
  genero CHAR(1) NULL,
  PRIMARY KEY(idPessoa),
  FOREIGN KEY(idPessoa)
    REFERENCES Pessoas(idPessoa)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE PessoasJuridicas (
  idPessoa BIGINT UNSIGNED NOT NULL,
  razaoSocial VARCHAR(100) NULL,
  cnpj VARCHAR(14) NULL,
  ie VARCHAR(14) NULL,
  im VARCHAR(30) NULL,
  nomeRepresentante VARCHAR(50) NULL,
  sobrenomeRepresentante VARCHAR(100) NULL,
  PRIMARY KEY(idPessoa),
  FOREIGN KEY(idPessoa)
    REFERENCES Pessoas(idPessoa)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Contas (
  idConta BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  sigla VARCHAR(2) NULL,
  descricao VARCHAR(30) NULL,
  saldo DECIMAL(9,2) NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idConta),
  FOREIGN KEY(idCondominio)
    REFERENCES Condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE ContasBancarias (
  idConta BIGINT UNSIGNED NOT NULL,
  tipo CHAR NULL,
  banco VARCHAR(3) NULL,
  agencia VARCHAR(5) NULL,
  agenciaDv CHAR NULL,
  conta VARCHAR(20) NULL,
  contaDv CHAR NULL,
  PRIMARY KEY(idConta),
  FOREIGN KEY(idConta)
    REFERENCES Contas(idConta)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Categorias (
  idCategoria BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  tipo CHAR NULL,
  descricao VARCHAR(50) NULL,
  nivel INTEGER UNSIGNED NULL,
  ordem VARCHAR(255) NULL,
  idCategoriaPai BIGINT UNSIGNED NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idCategoria),
  FOREIGN KEY(idCondominio)
    REFERENCES Condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idCategoriaPai)
    REFERENCES Categorias(idCategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Subcategorias (
  idSubcategoria BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(50) NULL,
  idCategoria BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idSubcategoria),
  FOREIGN KEY(idCategoria)
    REFERENCES Categorias(idCategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Pessoa_Moradia (
  idPessoa BIGINT UNSIGNED NOT NULL,
  idMoradia BIGINT UNSIGNED NOT NULL,
  tipo CHAR NULL,
  participacaoDono FLOAT NULL,
  dataEntrada DATE NULL,
  dataSaida DATE NULL,
  PRIMARY KEY(idPessoa, idMoradia),
  FOREIGN KEY(idPessoa)
    REFERENCES Pessoas(idPessoa)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idMoradia)
    REFERENCES Moradias(idMoradia)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Cobrancas (
  idCobranca BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  idMoradia BIGINT UNSIGNED NOT NULL,
  motivoEmissao CHAR NULL,
  numero VARCHAR(10) NULL,
  parcela VARCHAR(3) NULL,
  dataEmissao DATE NULL,
  dataVencimento DATE NULL,
  valor DECIMAL(9,2) NULL,
  desconto DECIMAL(9,2) NULL,
  abatimento DECIMAL(9,2) NULL,
  outrasDeducoes DECIMAL(9,2) NULL,
  jurosMora DECIMAL(9,2) NULL,
  multa DECIMAL(9,2) NULL,
  outrosAcrescimos DECIMAL(9,2) NULL,
  total DECIMAL(9,2) NULL,
  descricao VARCHAR(255) NULL,
  percentualJurosMes FLOAT NULL,
  percentualMulta FLOAT NULL,
  situacao CHAR NULL,
  dataRecebimento DATE NULL,
  motivoBaixa CHAR NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idCobranca),
  FOREIGN KEY(idCondominio)
    REFERENCES Condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idMoradia)
    REFERENCES Moradias(idMoradia)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Periodos (
  idPeriodo BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  inicio DATE NULL,
  fim DATE NULL,
  encerrado BOOL NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idPeriodo),
  FOREIGN KEY(idCondominio)
    REFERENCES Condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Orcamentos (
  idOrcamento BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  idPeriodo BIGINT UNSIGNED NOT NULL,
  idSubcategoria BIGINT UNSIGNED NOT NULL,
  orcado DECIMAL(9,2) NULL,
  PRIMARY KEY(idOrcamento),
  FOREIGN KEY(idSubcategoria)
    REFERENCES Subcategorias(idSubcategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idPeriodo)
    REFERENCES Periodos(idPeriodo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE Lancamentos (
  idLancamento BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  idPeriodo BIGINT UNSIGNED NOT NULL,
  idSubcategoria BIGINT UNSIGNED NOT NULL,
  data DATE NULL,
  valor DECIMAL(9,2) NULL,
  descricao VARCHAR(255) NULL,
  idConta BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idLancamento),
  FOREIGN KEY(idConta)
    REFERENCES Contas(idConta)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idSubcategoria)
    REFERENCES Subcategorias(idSubcategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idPeriodo)
    REFERENCES Periodos(idPeriodo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);