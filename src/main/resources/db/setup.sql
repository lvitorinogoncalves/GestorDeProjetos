CREATE TABLE pessoa (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  datanascimento DATE,
  cpf VARCHAR(14),
  funcionario BOOLEAN,
  gerente BOOLEAN
);

CREATE TABLE membro (
  id BIGSERIAL PRIMARY KEY,
  idpessoa BIGINT NOT NULL,
  atribuicao VARCHAR(100),
  CONSTRAINT fk_pessoa FOREIGN KEY (idpessoa) REFERENCES pessoa(id)
);

CREATE TABLE projeto (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(200) NOT NULL,
  data_inicio DATE,
  data_previsao_fim DATE,
  data_fim DATE,
  descricao VARCHAR(5000),
  status VARCHAR(45),
  orcamento FLOAT,
  risco VARCHAR(45),
  idgerente BIGINT NOT NULL,
  CONSTRAINT fk_gerente FOREIGN KEY (idgerente) REFERENCES pessoa(id)
);

CREATE TABLE membro_projeto (
  idprojeto BIGINT NOT NULL,
  idmembro BIGINT NOT NULL,
  CONSTRAINT pk_membro_projeto PRIMARY KEY (idprojeto, idmembro),
  CONSTRAINT fk_projeto FOREIGN KEY (idprojeto) REFERENCES projeto(id),
  CONSTRAINT fk_membro FOREIGN KEY (idmembro) REFERENCES membro(id)
);