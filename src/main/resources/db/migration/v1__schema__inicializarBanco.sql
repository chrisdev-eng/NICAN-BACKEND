-- Ta, aqui e onde entra a parte do chirstian do banco...







-- Tabelas principais
CREATE TABLE IF NOT EXISTS admin (
    idAdmin SERIAL PRIMARY KEY, nome VARCHAR(100), login VARCHAR(100) UNIQUE, senha VARCHAR(255),
    criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP, atualizadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS usuario (
    idUsuario SERIAL PRIMARY KEY, nome VARCHAR(100), login VARCHAR(100) UNIQUE, senha VARCHAR(255),
    idAdmin INTEGER REFERENCES admin(idAdmin), perfil VARCHAR(20), ativo BOOLEAN DEFAULT true,
    criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP, atualizadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS almoxarifado (
    idItem SERIAL PRIMARY KEY, nome VARCHAR(100), categoria VARCHAR(50),
    quantidadeTotal INT DEFAULT 0, quantidadeDisponivel INT DEFAULT 0,
    idAdmin INTEGER REFERENCES admin(idAdmin),
    criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS requerimento (
    idRequerimento SERIAL PRIMARY KEY, idUsuario INT REFERENCES usuario(idUsuario),
    idItem INT REFERENCES almoxarifado(idItem), quantidadeSolicitada INT,
    status VARCHAR(20) DEFAULT 'pendente', idAdmin INT REFERENCES admin(idAdmin),
    dataSolicitacao DATE DEFAULT CURRENT_DATE, criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- (Opcional: Crie movimentacao_estoque e emprestar se quiser, mas as 4 acima cobrem o essencial)

-- TRIGGER 1: Atualiza data automaticamente
CREATE OR REPLACE FUNCTION trg_update_ts() RETURNS TRIGGER AS $$
BEGIN NEW.atualizadoEm = CURRENT_TIMESTAMP; RETURN NEW; END; $$ LANGUAGE plpgsql;
CREATE TRIGGER ts_usuario BEFORE UPDATE ON usuario FOR EACH ROW EXECUTE PROCEDURE trg_update_ts();

-- TRIGGER 2: Bloqueia requisição maior que o estoque (Regra de Negócio no Banco)
CREATE OR REPLACE FUNCTION trg_check_estoque() RETURNS TRIGGER AS $$
BEGIN
  IF (SELECT quantidadeDisponivel FROM almoxarifado WHERE idItem = NEW.idItem) < NEW.quantidadeSolicitada THEN
    RAISE EXCEPTION 'Estoque insuficiente!';
  END IF;
  RETURN NEW;
END; $$ LANGUAGE plpgsql;
CREATE TRIGGER check_estoque_req BEFORE INSERT ON requerimento FOR EACH ROW EXECUTE PROCEDURE trg_check_estoque();

-- TRIGGER 3: Log de auditoria simples
CREATE TABLE IF NOT EXISTS log_acoes (id SERIAL PRIMARY KEY, acao TEXT, data TIMESTAMP DEFAULT NOW());
CREATE OR REPLACE FUNCTION trg_log_req() RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO log_acoes (acao) VALUES ('Requerimento #' || NEW.idRequerimento || ' criado por user ' || NEW.idUsuario);
  RETURN NEW;
END; $$ LANGUAGE plpgsql;
CREATE TRIGGER log_requerimento AFTER INSERT ON requerimento FOR EACH ROW EXECUTE PROCEDURE trg_log_req();
