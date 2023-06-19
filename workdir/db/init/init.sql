CREATE TABLE Pessoa
(
    id                BIGINT       NOT NULL,
    version           BIGINT       NOT NULL,
    uuid              VARCHAR(40)  NOT NULL,
    nome              VARCHAR(255) NOT NULL,
    identificacao     VARCHAR(14)  NOT NULL,
    senha             VARCHAR(255) NOT NULL,
    `role`            VARCHAR(255) NULL,
    email             VARCHAR(255) NOT NULL,
    telefone          VARCHAR(12)  NOT NULL,
    tipoIdentificacao INT          NOT NULL,
    CONSTRAINT pk_pessoa PRIMARY KEY (id)
);

ALTER TABLE Pessoa
    ADD CONSTRAINT uc_pessoa_identificacao UNIQUE (identificacao);

INSERT INTO Pessoa (uuid, version, email, identificacao, nome, telefone, tipoIdentificacao, senha, role)
VALUES (uuid(), 0, 'admin@admin', '99999999999', 'admin', '9999999999', 0, 'PBKDF2WithHmacSHA256:2048:e1Dyx83B1AanfJ3JhWtV+kVIqsjADDqy/pHMvntEWBc=:sVH99XQF8DlVk9McbFPNUzLYPp4ySnKdq1EPbLNBKnM=', 'ADMIN');
