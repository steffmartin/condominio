START TRANSACTION;

--CREATE SCHEMA condominio DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_unicode_ci ;
--USE condominio;

CREATE TABLE condominios (
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

CREATE TABLE usuarios (
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
    REFERENCES condominios(idCondominio)
      ON DELETE SET NULL
      ON UPDATE CASCADE
);

CREATE TABLE autorizacoes (
  id_usuario BIGINT UNSIGNED NOT NULL,
  autorizacao VARCHAR(50) NOT NULL,
  PRIMARY KEY(id_usuario, autorizacao),
  FOREIGN KEY(id_usuario)
    REFERENCES usuarios(id)
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
    REFERENCES usuarios(username)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
);

CREATE TABLE blocos (
  idBloco BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  sigla VARCHAR(3) NULL,
  descricao VARCHAR(30) NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idBloco),
  FOREIGN KEY(idCondominio)
    REFERENCES condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE moradias (
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
    REFERENCES blocos(idBloco)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE pessoas (
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
    REFERENCES condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE pessoasfisicas (
  idPessoa BIGINT UNSIGNED NOT NULL,
  sobrenome VARCHAR(100) NULL,
  cpf VARCHAR(11) NULL,
  rg VARCHAR(14) NULL,
  nascimento DATE NULL,
  genero CHAR(1) NULL,
  PRIMARY KEY(idPessoa),
  FOREIGN KEY(idPessoa)
    REFERENCES pessoas(idPessoa)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE pessoasjuridicas (
  idPessoa BIGINT UNSIGNED NOT NULL,
  razaoSocial VARCHAR(100) NULL,
  cnpj VARCHAR(14) NULL,
  ie VARCHAR(14) NULL,
  im VARCHAR(30) NULL,
  nomeRepresentante VARCHAR(50) NULL,
  sobrenomeRepresentante VARCHAR(100) NULL,
  PRIMARY KEY(idPessoa),
  FOREIGN KEY(idPessoa)
    REFERENCES pessoas(idPessoa)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE contas (
  idConta BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  sigla VARCHAR(2) NULL,
  descricao VARCHAR(30) NULL,
  saldoInicial DECIMAL(9,2) NULL,
  saldoAtual DECIMAL(9,2) NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idConta),
  FOREIGN KEY(idCondominio)
    REFERENCES condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE contasbancarias (
  idConta BIGINT UNSIGNED NOT NULL,
  tipo CHAR NULL,
  banco VARCHAR(3) NULL,
  agencia VARCHAR(5) NULL,
  agenciaDv CHAR NULL,
  conta VARCHAR(20) NULL,
  contaDv CHAR NULL,
  PRIMARY KEY(idConta),
  FOREIGN KEY(idConta)
    REFERENCES contas(idConta)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE categorias (
  idCategoria BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  tipo CHAR NULL,
  descricao VARCHAR(50) NULL,
  nivel INTEGER UNSIGNED NULL,
  ordem VARCHAR(255) NULL,
  idCategoriaPai BIGINT UNSIGNED NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idCategoria),
  FOREIGN KEY(idCondominio)
    REFERENCES condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idCategoriaPai)
    REFERENCES categorias(idCategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE subcategorias (
  idSubcategoria BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(50) NULL,
  idCategoria BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idSubcategoria),
  FOREIGN KEY(idCategoria)
    REFERENCES categorias(idCategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE pessoa_moradia (
  idPessoa BIGINT UNSIGNED NOT NULL,
  idMoradia BIGINT UNSIGNED NOT NULL,
  tipo CHAR NULL,
  participacaoDono FLOAT NULL,
  dataEntrada DATE NULL,
  dataSaida DATE NULL,
  PRIMARY KEY(idPessoa, idMoradia),
  FOREIGN KEY(idPessoa)
    REFERENCES pessoas(idPessoa)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idMoradia)
    REFERENCES moradias(idMoradia)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE cobrancas (
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
    REFERENCES condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idMoradia)
    REFERENCES moradias(idMoradia)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE periodos (
  idPeriodo BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  inicio DATE NULL,
  fim DATE NULL,
  encerrado BOOL NULL,
  idCondominio BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idPeriodo),
  FOREIGN KEY(idCondominio)
    REFERENCES condominios(idCondominio)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE orcamentos (
  idOrcamento BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  idPeriodo BIGINT UNSIGNED NOT NULL,
  idSubcategoria BIGINT UNSIGNED NOT NULL,
  orcado DECIMAL(9,2) NULL,
  PRIMARY KEY(idOrcamento),
  FOREIGN KEY(idSubcategoria)
    REFERENCES subcategorias(idSubcategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idPeriodo)
    REFERENCES periodos(idPeriodo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE movimentos (
  idMovimento BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  data DATE NULL,
  valor DECIMAL(9,2) NULL,
  documento VARCHAR(20) NULL,
  descricao VARCHAR(255) NULL,
  reducao BOOL NULL,
  idConta BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idMovimento),
  FOREIGN KEY(idConta)
    REFERENCES contas(idConta)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE transferencias (
  idMovimento BIGINT UNSIGNED NOT NULL,
  idContaInversa BIGINT UNSIGNED NOT NULL,
  idMovimentoInverso BIGINT UNSIGNED NULL,
  PRIMARY KEY(idMovimento),
  FOREIGN KEY(idContaInversa)
    REFERENCES contas(idConta)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idMovimento)
    REFERENCES movimentos(idMovimento)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idMovimentoInverso)
    REFERENCES transferencias(idMovimento)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE lancamentos (
  idMovimento BIGINT UNSIGNED NOT NULL,
  idPeriodo BIGINT UNSIGNED NOT NULL,
  idSubcategoria BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(idMovimento),
  FOREIGN KEY(idSubcategoria)
    REFERENCES subcategorias(idSubcategoria)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idPeriodo)
    REFERENCES periodos(idPeriodo)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(idMovimento)
    REFERENCES movimentos(idMovimento)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

DELIMITER $$
CREATE TRIGGER atSaldoOnInsertMovimento
BEFORE INSERT ON movimentos
FOR EACH ROW
BEGIN
	IF NEW.reducao THEN
		UPDATE contas
        SET saldoAtual = saldoAtual - NEW.valor
        WHERE idConta = NEW.idConta;
    ELSE
		UPDATE contas
        SET saldoAtual = saldoAtual + NEW.valor
        WHERE idConta = NEW.idConta;
    END IF;
END;$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER atSaldoOnDeleteMovimento
BEFORE DELETE ON movimentos
FOR EACH ROW
BEGIN
	IF OLD.reducao THEN
		UPDATE contas
        SET saldoAtual = saldoAtual + OLD.valor
        WHERE idConta = OLD.idConta;
    ELSE
		UPDATE contas
        SET saldoAtual = saldoAtual - OLD.valor
        WHERE idConta = OLD.idConta;
    END IF;
END;$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER atSaldoOnUpdateMovimento
BEFORE UPDATE ON movimentos
FOR EACH ROW
BEGIN
	IF OLD.reducao THEN
		UPDATE contas
        SET saldoAtual = saldoAtual + OLD.valor
        WHERE idConta = OLD.idConta;
    ELSE
		UPDATE contas
        SET saldoAtual = saldoAtual - OLD.valor
        WHERE idConta = OLD.idConta;
    END IF;
	IF NEW.reducao THEN
		UPDATE contas
        SET saldoAtual = saldoAtual - NEW.valor
        WHERE idConta = NEW.idConta;
    ELSE
		UPDATE contas
        SET saldoAtual = saldoAtual + NEW.valor
        WHERE idConta = NEW.idConta;
    END IF;
END;$$
DELIMITER ;

DELIMITER $$
CREATE EVENT atTotalCobrancaDiariamente
ON SCHEDULE EVERY 1 DAY STARTS (CURRENT_DATE + INTERVAL 1 DAY + INTERVAL 1 MINUTE) DO
BEGIN
	UPDATE cobrancas
    SET
		total = total - multa - jurosMora,
		multa = valor * (percentualMulta/100),
        jurosMora = valor * (percentualJurosMes/30/100) * DATEDIFF(CURRENT_DATE,dataVencimento),
        total = total + multa + jurosMora
    WHERE
		dataRecebimento IS NULL
        AND dataVencimento < CURRENT_DATE;
END;$$
DELIMITER ;

DELIMITER $$
CREATE EVENT delRememberMe
ON SCHEDULE EVERY 1 DAY STARTS (CURRENT_DATE + INTERVAL 1 DAY + INTERVAL 12 HOUR) DO
BEGIN
	DELETE FROM persistent_logins
    WHERE
		(current_timestamp - last_used) > (120960 * 1000);
END;$$
DELIMITER ;

COMMIT;