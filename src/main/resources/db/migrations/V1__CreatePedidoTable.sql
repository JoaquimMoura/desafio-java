CREATE TABLE IF NOT EXISTS pedido (
    codigo BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_controle INT NOT NULL,
    data_cadastro DATE,
    nome VARCHAR(255) NOT NULL,
    valor DOUBLE NOT NULL,
    quantidade INT,
    codigo_cliente BIGINT NOT NULL
);

