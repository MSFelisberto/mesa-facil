CREATE TABLE IF NOT EXISTS enderecos
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    logradouro VARCHAR(100) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL
    );

CREATE TABLE IF NOT EXISTS usuarios
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_ultima_atualizacao DATE,
    tipo_usuario VARCHAR(100) NOT NULL,
    ativo SMALLINT NOT NULL,
    endereco_id BIGINT NOT NULL,
    CONSTRAINT fk_usuarios_enderecos FOREIGN KEY (endereco_id) REFERENCES enderecos(id)
    );





