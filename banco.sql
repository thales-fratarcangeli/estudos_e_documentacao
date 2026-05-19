-- ============================================================
-- DDL - DATA DEFINITION LANGUAGE
-- ============================================================

CREATE DATABASE loja;

USE loja;

CREATE SCHEMA vendas;

DROP DATABASE IF EXISTS loja_antiga;

CREATE TABLE clientes (
    id              INT             NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(100)    NOT NULL,
    email           VARCHAR(150)    UNIQUE,
    cpf             CHAR(11)        UNIQUE NOT NULL,
    data_nascimento DATE,
    saldo           DECIMAL(10, 2)  DEFAULT 0.00,
    ativo           BOOLEAN         DEFAULT TRUE,
    criado_em       TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    atualizado_em   TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE categorias (
    id   INT         NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE produtos (
    id           INT            NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(200)   NOT NULL,
    preco        DECIMAL(10, 2) NOT NULL,
    estoque      INT            DEFAULT 0,
    categoria_id INT,
    ativo        BOOLEAN        DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id)
        REFERENCES categorias (id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE pedidos (
    id          INT            NOT NULL AUTO_INCREMENT,
    cliente_id  INT            NOT NULL,
    total       DECIMAL(10, 2),
    status      ENUM('pendente', 'aprovado', 'enviado', 'entregue', 'cancelado') DEFAULT 'pendente',
    criado_em   TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id) REFERENCES clientes (id)
);

CREATE TABLE pedido_itens (
    id         INT            NOT NULL AUTO_INCREMENT,
    pedido_id  INT            NOT NULL,
    produto_id INT            NOT NULL,
    quantidade INT            NOT NULL,
    preco_unit DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (pedido_id)  REFERENCES pedidos  (id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos (id)
);

CREATE TABLE log_acoes (
    id         INT          NOT NULL AUTO_INCREMENT,
    tabela     VARCHAR(50),
    operacao   VARCHAR(10),
    usuario    VARCHAR(50)  DEFAULT CURRENT_USER(),
    momento    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    dados_json JSON,
    PRIMARY KEY (id)
);

ALTER TABLE clientes ADD COLUMN telefone VARCHAR(20);
ALTER TABLE clientes ADD COLUMN cidade VARCHAR(80) AFTER email;
ALTER TABLE clientes MODIFY COLUMN nome VARCHAR(150) NOT NULL;
ALTER TABLE clientes RENAME COLUMN telefone TO celular;
ALTER TABLE clientes DROP COLUMN cidade;
ALTER TABLE clientes ADD CONSTRAINT chk_saldo CHECK (saldo >= 0);

ALTER TABLE produtos ADD COLUMN descricao TEXT;
ALTER TABLE produtos ADD COLUMN peso FLOAT;
ALTER TABLE produtos ADD INDEX idx_nome (nome);
ALTER TABLE produtos ADD FULLTEXT INDEX ft_nome_desc (nome, descricao);

RENAME TABLE log_acoes TO logs;
RENAME TABLE logs      TO log_acoes;

TRUNCATE TABLE log_acoes;

DROP TABLE IF EXISTS tabela_temporaria;

CREATE TEMPORARY TABLE tmp_resumo (
    cliente_id INT,
    total_gasto DECIMAL(10, 2)
);

-- ============================================================
-- CONSTRAINTS
-- ============================================================

ALTER TABLE produtos ADD CONSTRAINT chk_preco   CHECK (preco > 0);
ALTER TABLE produtos ADD CONSTRAINT chk_estoque CHECK (estoque >= 0);

-- ============================================================
-- INDEXES
-- ============================================================

CREATE INDEX idx_cliente_email     ON clientes (email);
CREATE INDEX idx_pedido_cliente    ON pedidos  (cliente_id);
CREATE INDEX idx_pedido_status     ON pedidos  (status);
CREATE UNIQUE INDEX uq_cpf         ON clientes (cpf);

DROP INDEX idx_cliente_email ON clientes;

-- ============================================================
-- SEQUENCES / AUTO_INCREMENT (sintaxe padrão PostgreSQL)
-- ============================================================

-- CREATE SEQUENCE seq_clientes START 1 INCREMENT 1;
-- SELECT NEXTVAL('seq_clientes');
-- SELECT CURRVAL('seq_clientes');
-- ALTER SEQUENCE seq_clientes RESTART WITH 100;

-- ============================================================
-- DML - DATA MANIPULATION LANGUAGE
-- ============================================================

INSERT INTO categorias (nome) VALUES ('Eletrônicos');
INSERT INTO categorias (nome) VALUES ('Roupas');
INSERT INTO categorias (nome) VALUES ('Alimentos');

INSERT INTO categorias (nome)
VALUES ('Livros'), ('Esportes'), ('Beleza');

INSERT INTO clientes (nome, email, cpf, data_nascimento, saldo)
VALUES
    ('Ana Silva',    'ana@email.com',    '11111111111', '1990-03-15', 500.00),
    ('Bruno Souza',  'bruno@email.com',  '22222222222', '1985-07-22', 1200.50),
    ('Carla Mendes', 'carla@email.com',  '33333333333', '2000-11-01', 0.00),
    ('Diego Lima',   'diego@email.com',  '44444444444', '1978-05-30', 300.00),
    ('Elena Costa',  'elena@email.com',  '55555555555', '1995-09-18', 750.00);

INSERT INTO produtos (nome, preco, estoque, categoria_id, descricao, peso)
VALUES
    ('Notebook',        3500.00, 10, 1, 'Notebook 15 polegadas', 1.8),
    ('Smartphone',      1800.00, 25, 1, 'Celular Android',       0.2),
    ('Camiseta Básica',   59.90, 100,2, 'Camiseta algodão',      0.3),
    ('Calça Jeans',      149.90, 50, 2, 'Calça slim fit',        0.7),
    ('Arroz 5kg',         25.00, 200,3, 'Arroz tipo 1',          5.0),
    ('Livro SQL',         89.90, 30, 4, 'SQL do zero ao avançado',0.5),
    ('Tênis Running',    299.00, 40, 5, 'Tênis para corrida',    0.8);

INSERT INTO pedidos (cliente_id, status)
VALUES (1, 'aprovado'), (2, 'entregue'), (3, 'pendente'), (1, 'enviado');

INSERT INTO pedido_itens (pedido_id, produto_id, quantidade, preco_unit)
VALUES
    (1, 1, 1, 3500.00),
    (1, 3, 2,   59.90),
    (2, 2, 1, 1800.00),
    (3, 5, 3,   25.00),
    (4, 6, 1,   89.90),
    (4, 7, 1,  299.00);

INSERT INTO clientes (nome, email, cpf)
SELECT CONCAT('Cliente ', id), CONCAT('cli', id, '@test.com'), LPAD(id * 111, 11, '0')
FROM produtos
WHERE id > 5;

UPDATE clientes SET saldo = saldo + 100 WHERE id = 3;

UPDATE clientes
SET saldo = saldo * 1.10,
    ativo = TRUE
WHERE data_nascimento < '1990-01-01';

UPDATE produtos p
JOIN categorias c ON p.categoria_id = c.id
SET p.preco = p.preco * 0.95
WHERE c.nome = 'Eletrônicos';

UPDATE pedidos SET total = (
    SELECT SUM(quantidade * preco_unit)
    FROM pedido_itens
    WHERE pedido_id = pedidos.id
);

DELETE FROM clientes WHERE ativo = FALSE;

DELETE FROM pedido_itens WHERE pedido_id IN (
    SELECT id FROM pedidos WHERE status = 'cancelado'
);

DELETE c FROM clientes c
LEFT JOIN pedidos p ON c.id = p.cliente_id
WHERE p.id IS NULL AND c.criado_em < DATE_SUB(NOW(), INTERVAL 1 YEAR);

-- ============================================================
-- SELECT - BÁSICO AO AVANÇADO
-- ============================================================

SELECT * FROM clientes;

SELECT nome, email, saldo FROM clientes;

SELECT DISTINCT status FROM pedidos;

SELECT nome, preco, estoque, preco * estoque AS valor_total_estoque FROM produtos;

SELECT nome, preco,
    CASE
        WHEN preco < 100  THEN 'Barato'
        WHEN preco < 1000 THEN 'Médio'
        ELSE 'Caro'
    END AS faixa_preco
FROM produtos;

SELECT * FROM clientes WHERE saldo > 500;
SELECT * FROM clientes WHERE saldo BETWEEN 300 AND 1000;
SELECT * FROM clientes WHERE nome LIKE 'A%';
SELECT * FROM clientes WHERE nome LIKE '%Silva';
SELECT * FROM clientes WHERE nome LIKE '%li%';
SELECT * FROM clientes WHERE email IS NOT NULL;
SELECT * FROM clientes WHERE id IN (1, 3, 5);
SELECT * FROM clientes WHERE id NOT IN (2, 4);
SELECT * FROM produtos WHERE categoria_id IS NULL;

SELECT * FROM clientes ORDER BY nome ASC;
SELECT * FROM clientes ORDER BY saldo DESC;
SELECT * FROM clientes ORDER BY ativo DESC, nome ASC;

SELECT * FROM produtos LIMIT 5;
SELECT * FROM produtos LIMIT 5 OFFSET 10;

-- ============================================================
-- JOINS
-- ============================================================

SELECT p.nome, c.nome AS categoria
FROM produtos p
INNER JOIN categorias c ON p.categoria_id = c.id;

SELECT cl.nome, pe.id AS pedido_id, pe.status
FROM clientes cl
LEFT JOIN pedidos pe ON cl.id = pe.cliente_id;

SELECT pe.id, cl.nome
FROM pedidos pe
RIGHT JOIN clientes cl ON pe.cliente_id = cl.id;

SELECT cl.nome, pe.id AS pedido_id
FROM clientes cl
CROSS JOIN pedidos pe;

SELECT cl.nome AS cliente, c2.nome AS indicado_por
FROM clientes cl
LEFT JOIN clientes c2 ON cl.id = c2.id + 1;

SELECT cl.nome, SUM(pi.quantidade * pi.preco_unit) AS total_gasto
FROM clientes cl
JOIN pedidos pe     ON cl.id = pe.cliente_id
JOIN pedido_itens pi ON pe.id = pi.pedido_id
GROUP BY cl.id, cl.nome
ORDER BY total_gasto DESC;

-- ============================================================
-- GROUP BY / HAVING
-- ============================================================

SELECT categoria_id, COUNT(*) AS total_produtos, AVG(preco) AS preco_medio
FROM produtos
GROUP BY categoria_id;

SELECT categoria_id, COUNT(*) AS total
FROM produtos
GROUP BY categoria_id
HAVING COUNT(*) >= 2;

SELECT status, COUNT(*) AS qtd, SUM(total) AS valor_total
FROM pedidos
GROUP BY status
HAVING valor_total > 100;

SELECT categoria_id, SUM(estoque) AS total_estoque
FROM produtos
GROUP BY categoria_id WITH ROLLUP;

-- ============================================================
-- SUBQUERIES
-- ============================================================

SELECT nome, preco
FROM produtos
WHERE preco > (SELECT AVG(preco) FROM produtos);

SELECT nome FROM clientes
WHERE id IN (SELECT DISTINCT cliente_id FROM pedidos);

SELECT nome FROM clientes
WHERE id NOT IN (SELECT DISTINCT cliente_id FROM pedidos WHERE status = 'entregue');

SELECT nome FROM clientes
WHERE EXISTS (
    SELECT 1 FROM pedidos WHERE pedidos.cliente_id = clientes.id
);

SELECT nome FROM clientes
WHERE NOT EXISTS (
    SELECT 1 FROM pedidos WHERE pedidos.cliente_id = clientes.id AND status = 'cancelado'
);

SELECT nome, preco
FROM produtos
WHERE preco > ALL (SELECT preco FROM produtos WHERE categoria_id = 3);

SELECT nome, preco
FROM produtos
WHERE preco > ANY (SELECT preco FROM produtos WHERE categoria_id = 2);

SELECT cl.nome, sub.total_pedidos
FROM clientes cl
JOIN (
    SELECT cliente_id, COUNT(*) AS total_pedidos
    FROM pedidos
    GROUP BY cliente_id
) sub ON cl.id = sub.cliente_id;

-- ============================================================
-- CTE - COMMON TABLE EXPRESSIONS
-- ============================================================

WITH cte_clientes_ativos AS (
    SELECT id, nome, saldo
    FROM clientes
    WHERE ativo = TRUE
)
SELECT * FROM cte_clientes_ativos WHERE saldo > 0;

WITH
    pedidos_por_cliente AS (
        SELECT cliente_id, COUNT(*) AS qtd_pedidos, SUM(total) AS total_gasto
        FROM pedidos
        GROUP BY cliente_id
    ),
    top_clientes AS (
        SELECT cliente_id FROM pedidos_por_cliente WHERE total_gasto > 1000
    )
SELECT cl.nome, pc.qtd_pedidos, pc.total_gasto
FROM clientes cl
JOIN pedidos_por_cliente pc ON cl.id = pc.cliente_id
WHERE cl.id IN (SELECT cliente_id FROM top_clientes);

WITH RECURSIVE hierarquia (id, nome, nivel) AS (
    SELECT id, nome, 0 AS nivel
    FROM categorias
    WHERE id = 1
    UNION ALL
    SELECT c.id, c.nome, h.nivel + 1
    FROM categorias c
    JOIN hierarquia h ON c.id = h.id + 1
    WHERE h.nivel < 5
)
SELECT * FROM hierarquia;

-- ============================================================
-- WINDOW FUNCTIONS
-- ============================================================

SELECT
    nome,
    preco,
    categoria_id,
    ROW_NUMBER()  OVER (PARTITION BY categoria_id ORDER BY preco DESC) AS row_num,
    RANK()        OVER (PARTITION BY categoria_id ORDER BY preco DESC) AS ranking,
    DENSE_RANK()  OVER (PARTITION BY categoria_id ORDER BY preco DESC) AS dense_rank,
    NTILE(4)      OVER (ORDER BY preco)                                 AS quartil,
    PERCENT_RANK()OVER (ORDER BY preco)                                 AS pct_rank,
    CUME_DIST()   OVER (ORDER BY preco)                                 AS cume_dist
FROM produtos;

SELECT
    nome,
    preco,
    categoria_id,
    SUM(preco)   OVER (PARTITION BY categoria_id)                       AS total_categoria,
    AVG(preco)   OVER (PARTITION BY categoria_id)                       AS media_categoria,
    MAX(preco)   OVER ()                                                 AS max_global,
    SUM(preco)   OVER (ORDER BY id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS acumulado
FROM produtos;

SELECT
    id,
    preco,
    LAG(preco)           OVER (ORDER BY id) AS preco_anterior,
    LEAD(preco)          OVER (ORDER BY id) AS preco_proximo,
    FIRST_VALUE(preco)   OVER (ORDER BY id) AS primeiro_preco,
    LAST_VALUE(preco)    OVER (ORDER BY id ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) AS ultimo_preco,
    NTH_VALUE(preco, 2)  OVER (ORDER BY id) AS segundo_preco
FROM produtos;

-- ============================================================
-- FUNÇÕES DE STRING
-- ============================================================

SELECT
    UPPER('hello world'),
    LOWER('HELLO WORLD'),
    LENGTH('texto'),
    CHAR_LENGTH('texto'),
    CONCAT('Olá', ' ', 'Mundo'),
    CONCAT_WS(', ', 'Ana', 'Bruno', 'Carla'),
    TRIM('   espaço   '),
    LTRIM('   espaço'),
    RTRIM('espaço   '),
    LPAD('42', 5, '0'),
    RPAD('42', 5, '0'),
    SUBSTRING('Hello World', 7, 5),
    SUBSTR('Hello World', 1, 5),
    LEFT('Hello', 3),
    RIGHT('World', 3),
    INSTR('Hello World', 'World'),
    LOCATE('World', 'Hello World'),
    REPLACE('foo bar foo', 'foo', 'baz'),
    REVERSE('abcde'),
    REPEAT('ab', 3),
    SPACE(5),
    ASCII('A'),
    CHAR(65),
    HEX('A'),
    UNHEX('41'),
    FORMAT(1234567.891, 2),
    STRCMP('abc', 'abd'),
    SOUNDEX('Smith'),
    MD5('senha123'),
    SHA1('senha123'),
    SHA2('senha123', 256);

SELECT REGEXP_LIKE('joao@email.com', '^[a-z]+@[a-z]+\\.[a-z]+$');
SELECT REGEXP_REPLACE('abc123def', '[0-9]+', 'NUM');
SELECT REGEXP_SUBSTR('preco: 99.90', '[0-9]+\\.[0-9]+');

-- ============================================================
-- FUNÇÕES NUMÉRICAS
-- ============================================================

SELECT
    ABS(-42),
    CEIL(4.2),
    CEILING(4.2),
    FLOOR(4.9),
    ROUND(4.5678, 2),
    TRUNCATE(4.9999, 2),
    MOD(10, 3),
    10 % 3,
    POWER(2, 8),
    POW(2, 10),
    SQRT(144),
    EXP(1),
    LN(2.718),
    LOG(10, 100),
    LOG2(8),
    LOG10(1000),
    PI(),
    SIN(PI() / 2),
    COS(0),
    TAN(PI() / 4),
    DEGREES(PI()),
    RADIANS(180),
    SIGN(-5),
    SIGN(0),
    SIGN(7),
    RAND(),
    RAND(42),
    GREATEST(10, 20, 5, 30, 1),
    LEAST(10, 20, 5, 30, 1);

-- ============================================================
-- FUNÇÕES DE DATA E HORA
-- ============================================================

SELECT
    NOW(),
    CURRENT_TIMESTAMP(),
    CURDATE(),
    CURRENT_DATE(),
    CURTIME(),
    CURRENT_TIME(),
    SYSDATE(),
    UTC_DATE(),
    UTC_TIME(),
    UTC_TIMESTAMP();

SELECT
    YEAR(NOW()),
    MONTH(NOW()),
    DAY(NOW()),
    HOUR(NOW()),
    MINUTE(NOW()),
    SECOND(NOW()),
    DAYOFWEEK(NOW()),
    DAYOFMONTH(NOW()),
    DAYOFYEAR(NOW()),
    WEEKDAY(NOW()),
    WEEK(NOW()),
    QUARTER(NOW()),
    MONTHNAME(NOW()),
    DAYNAME(NOW());

SELECT
    DATE_ADD(NOW(), INTERVAL 7 DAY),
    DATE_ADD(NOW(), INTERVAL 1 MONTH),
    DATE_ADD(NOW(), INTERVAL 2 YEAR),
    DATE_SUB(NOW(), INTERVAL 30 DAY),
    ADDDATE('2025-01-01', 30),
    SUBDATE('2025-01-01', 10),
    ADDTIME('10:00:00', '01:30:00'),
    SUBTIME('10:00:00', '00:30:00');

SELECT
    DATEDIFF('2025-12-31', '2025-01-01'),
    TIMEDIFF('10:30:00', '08:00:00'),
    TIMESTAMPDIFF(MONTH, '2024-01-01', '2025-01-01'),
    TIMESTAMPDIFF(DAY,   '2025-01-01', NOW());

SELECT
    DATE_FORMAT(NOW(), '%d/%m/%Y %H:%i:%s'),
    DATE_FORMAT(NOW(), '%W, %M %d %Y'),
    STR_TO_DATE('25/12/2025', '%d/%m/%Y'),
    TIME_FORMAT(NOW(), '%H:%i:%s'),
    FROM_UNIXTIME(0),
    UNIX_TIMESTAMP(NOW()),
    EXTRACT(YEAR FROM NOW()),
    EXTRACT(MONTH FROM NOW()),
    LAST_DAY('2025-02-01'),
    MAKEDATE(2025, 100),
    MAKETIME(10, 30, 0),
    PERIOD_ADD(202501, 3),
    PERIOD_DIFF(202512, 202501),
    TO_DAYS('2025-01-01'),
    FROM_DAYS(738886),
    TO_SECONDS('2025-01-01 00:00:00');

-- ============================================================
-- FUNÇÕES DE AGREGAÇÃO
-- ============================================================

SELECT
    COUNT(*)                        AS total_linhas,
    COUNT(DISTINCT categoria_id)    AS categorias_distintas,
    SUM(preco)                      AS soma_precos,
    AVG(preco)                      AS media_precos,
    MAX(preco)                      AS maior_preco,
    MIN(preco)                      AS menor_preco,
    STDDEV(preco)                   AS desvio_padrao,
    STDDEV_POP(preco)               AS desvio_pop,
    STDDEV_SAMP(preco)              AS desvio_samp,
    VARIANCE(preco)                 AS variancia,
    VAR_POP(preco)                  AS var_pop,
    VAR_SAMP(preco)                 AS var_samp,
    GROUP_CONCAT(nome ORDER BY nome SEPARATOR ', ') AS lista_produtos
FROM produtos;

-- ============================================================
-- FUNÇÕES CONDICIONAIS
-- ============================================================

SELECT
    IFNULL(NULL, 'valor padrão'),
    IFNULL('valor', 'padrão'),
    NULLIF(10, 10),
    NULLIF(10, 20),
    IF(1 > 0, 'verdadeiro', 'falso'),
    COALESCE(NULL, NULL, 'primeiro não nulo', 'outro');

SELECT
    nome,
    CASE status
        WHEN 'pendente'  THEN 'Aguardando'
        WHEN 'aprovado'  THEN 'OK'
        WHEN 'entregue'  THEN 'Finalizado'
        ELSE 'Outro'
    END AS status_desc
FROM pedidos;

SELECT
    nome,
    CASE
        WHEN saldo = 0    THEN 'Sem saldo'
        WHEN saldo < 500  THEN 'Baixo'
        WHEN saldo < 1000 THEN 'Médio'
        ELSE 'Alto'
    END AS faixa
FROM clientes;

-- ============================================================
-- FUNÇÕES DE SISTEMA / INFORMAÇÃO
-- ============================================================

SELECT
    VERSION(),
    DATABASE(),
    SCHEMA(),
    USER(),
    CURRENT_USER(),
    CONNECTION_ID(),
    LAST_INSERT_ID(),
    ROW_COUNT(),
    FOUND_ROWS();

-- ============================================================
-- FUNÇÕES JSON
-- ============================================================

SELECT JSON_OBJECT('nome', 'Ana', 'idade', 30);
SELECT JSON_ARRAY(1, 2, 3, 'quatro');

SET @json = '{"nome": "Ana", "enderecos": [{"cidade": "SP"}, {"cidade": "RJ"}]}';

SELECT
    JSON_EXTRACT(@json, '$.nome'),
    JSON_EXTRACT(@json, '$.enderecos[0].cidade'),
    @json -> '$.nome',
    @json ->> '$.nome',
    JSON_UNQUOTE(JSON_EXTRACT(@json, '$.nome')),
    JSON_LENGTH(@json),
    JSON_LENGTH(@json, '$.enderecos'),
    JSON_TYPE(JSON_EXTRACT(@json, '$.nome')),
    JSON_VALID(@json),
    JSON_KEYS(@json),
    JSON_CONTAINS(@json, '"Ana"', '$.nome'),
    JSON_SEARCH(@json, 'one', 'SP'),
    JSON_SET(@json, '$.idade', 31),
    JSON_INSERT(@json, '$.ativo', TRUE),
    JSON_REPLACE(@json, '$.nome', 'Beatriz'),
    JSON_REMOVE(@json, '$.enderecos');

-- ============================================================
-- VIEWS
-- ============================================================

CREATE VIEW vw_clientes_ativos AS
SELECT id, nome, email, saldo
FROM clientes
WHERE ativo = TRUE;

CREATE OR REPLACE VIEW vw_resumo_pedidos AS
SELECT
    cl.id AS cliente_id,
    cl.nome AS cliente,
    COUNT(pe.id)    AS total_pedidos,
    SUM(pe.total)   AS valor_total,
    MAX(pe.criado_em) AS ultimo_pedido
FROM clientes cl
LEFT JOIN pedidos pe ON cl.id = pe.cliente_id
GROUP BY cl.id, cl.nome;

CREATE OR REPLACE VIEW vw_produtos_com_categoria AS
SELECT p.id, p.nome, p.preco, p.estoque, c.nome AS categoria
FROM produtos p
LEFT JOIN categorias c ON p.categoria_id = c.id;

CREATE OR REPLACE VIEW vw_itens_detalhados AS
SELECT
    pi.id,
    pe.id AS pedido_id,
    cl.nome AS cliente,
    pr.nome AS produto,
    pi.quantidade,
    pi.preco_unit,
    pi.quantidade * pi.preco_unit AS subtotal
FROM pedido_itens pi
JOIN pedidos  pe ON pi.pedido_id  = pe.id
JOIN clientes cl ON pe.cliente_id = cl.id
JOIN produtos pr ON pi.produto_id = pr.id;

SELECT * FROM vw_clientes_ativos;
SELECT * FROM vw_resumo_pedidos ORDER BY valor_total DESC;

DROP VIEW IF EXISTS vw_clientes_ativos;

-- ============================================================
-- STORED PROCEDURES
-- ============================================================

DELIMITER $$

CREATE PROCEDURE sp_listar_clientes()
BEGIN
    SELECT * FROM clientes WHERE ativo = TRUE ORDER BY nome;
END$$

CREATE PROCEDURE sp_buscar_cliente(IN p_id INT)
BEGIN
    SELECT * FROM clientes WHERE id = p_id;
END$$

CREATE PROCEDURE sp_inserir_cliente(
    IN  p_nome  VARCHAR(150),
    IN  p_email VARCHAR(150),
    IN  p_cpf   CHAR(11),
    OUT p_novo_id INT
)
BEGIN
    INSERT INTO clientes (nome, email, cpf) VALUES (p_nome, p_email, p_cpf);
    SET p_novo_id = LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_atualizar_saldo(
    IN  p_cliente_id INT,
    IN  p_valor      DECIMAL(10,2),
    OUT p_sucesso    BOOLEAN
)
BEGIN
    DECLARE v_saldo_atual DECIMAL(10,2);

    SELECT saldo INTO v_saldo_atual FROM clientes WHERE id = p_cliente_id;

    IF v_saldo_atual + p_valor >= 0 THEN
        UPDATE clientes SET saldo = saldo + p_valor WHERE id = p_cliente_id;
        SET p_sucesso = TRUE;
    ELSE
        SET p_sucesso = FALSE;
    END IF;
END$$

CREATE PROCEDURE sp_criar_pedido(
    IN  p_cliente_id INT,
    OUT p_pedido_id  INT
)
BEGIN
    DECLARE v_existe INT;

    SELECT COUNT(*) INTO v_existe FROM clientes WHERE id = p_cliente_id AND ativo = TRUE;

    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cliente não encontrado ou inativo';
    END IF;

    INSERT INTO pedidos (cliente_id, status) VALUES (p_cliente_id, 'pendente');
    SET p_pedido_id = LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_relatorio_vendas(IN p_data_inicio DATE, IN p_data_fim DATE)
BEGIN
    SELECT
        DATE(pe.criado_em) AS data,
        COUNT(pe.id)        AS total_pedidos,
        SUM(pe.total)       AS faturamento
    FROM pedidos pe
    WHERE DATE(pe.criado_em) BETWEEN p_data_inicio AND p_data_fim
      AND pe.status != 'cancelado'
    GROUP BY DATE(pe.criado_em)
    ORDER BY data;

    SELECT
        pr.nome AS produto,
        SUM(pi.quantidade) AS qtd_vendida,
        SUM(pi.quantidade * pi.preco_unit) AS receita
    FROM pedido_itens pi
    JOIN pedidos pe  ON pi.pedido_id  = pe.id
    JOIN produtos pr ON pi.produto_id = pr.id
    WHERE DATE(pe.criado_em) BETWEEN p_data_inicio AND p_data_fim
      AND pe.status != 'cancelado'
    GROUP BY pr.id, pr.nome
    ORDER BY receita DESC;
END$$

CREATE PROCEDURE sp_demo_variaveis()
BEGIN
    DECLARE v_contador INT DEFAULT 0;
    DECLARE v_nome     VARCHAR(100);
    DECLARE v_total    DECIMAL(10,2) DEFAULT 0.00;
    DECLARE v_continua BOOLEAN DEFAULT TRUE;

    DECLARE cur CURSOR FOR SELECT nome FROM clientes WHERE ativo = TRUE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_continua = FALSE;

    OPEN cur;

    loop_cursor: LOOP
        FETCH cur INTO v_nome;

        IF NOT v_continua THEN
            LEAVE loop_cursor;
        END IF;

        SET v_contador = v_contador + 1;

        SELECT CONCAT(v_contador, ': ', v_nome) AS linha;
    END LOOP;

    CLOSE cur;
    SELECT v_contador AS total_processado;
END$$

CREATE PROCEDURE sp_demo_loops()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 5 DO
        SELECT i AS valor_while;
        SET i = i + 1;
    END WHILE;

    SET i = 1;
    REPEAT
        SELECT i AS valor_repeat;
        SET i = i + 1;
    UNTIL i > 5
    END REPEAT;

    SET i = 1;
    loop_simples: LOOP
        IF i > 5 THEN
            LEAVE loop_simples;
        END IF;
        SELECT i AS valor_loop;
        SET i = i + 1;
    END LOOP;
END$$

CREATE PROCEDURE sp_demo_handler()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'Erro: transação desfeita' AS mensagem;
    END;

    START TRANSACTION;
    UPDATE clientes SET saldo = saldo - 100 WHERE id = 1;
    UPDATE clientes SET saldo = saldo + 100 WHERE id = 2;
    COMMIT;
END$$

DELIMITER ;

CALL sp_listar_clientes();
CALL sp_buscar_cliente(1);

SET @novo_id = 0;
CALL sp_inserir_cliente('Novo Cliente', 'novo@email.com', '99999999999', @novo_id);
SELECT @novo_id;

SET @ok = FALSE;
CALL sp_atualizar_saldo(1, -200.00, @ok);
SELECT @ok;

CALL sp_relatorio_vendas('2025-01-01', '2025-12-31');

DROP PROCEDURE IF EXISTS sp_listar_clientes;

-- ============================================================
-- STORED FUNCTIONS
-- ============================================================

DELIMITER $$

CREATE FUNCTION fn_idade(p_nascimento DATE)
RETURNS INT
DETERMINISTIC
BEGIN
    RETURN TIMESTAMPDIFF(YEAR, p_nascimento, CURDATE());
END$$

CREATE FUNCTION fn_desconto(p_preco DECIMAL(10,2), p_pct DECIMAL(5,2))
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN ROUND(p_preco * (1 - p_pct / 100), 2);
END$$

CREATE FUNCTION fn_total_pedidos_cliente(p_cliente_id INT)
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE v_total INT;
    SELECT COUNT(*) INTO v_total FROM pedidos WHERE cliente_id = p_cliente_id;
    RETURN v_total;
END$$

CREATE FUNCTION fn_classificar_cliente(p_cliente_id INT)
RETURNS VARCHAR(20)
READS SQL DATA
BEGIN
    DECLARE v_total DECIMAL(10,2);
    SELECT COALESCE(SUM(total), 0) INTO v_total FROM pedidos WHERE cliente_id = p_cliente_id;

    RETURN CASE
        WHEN v_total >= 10000 THEN 'Platinum'
        WHEN v_total >= 5000  THEN 'Gold'
        WHEN v_total >= 1000  THEN 'Silver'
        ELSE 'Bronze'
    END;
END$$

DELIMITER ;

SELECT nome, fn_idade(data_nascimento) AS idade FROM clientes;
SELECT nome, preco, fn_desconto(preco, 10) AS preco_com_desconto FROM produtos;
SELECT nome, fn_total_pedidos_cliente(id) AS qtd_pedidos FROM clientes;
SELECT nome, fn_classificar_cliente(id) AS classificacao FROM clientes;

DROP FUNCTION IF EXISTS fn_idade;

-- ============================================================
-- TRIGGERS
-- ============================================================

DELIMITER $$

CREATE TRIGGER trg_pedido_before_insert
BEFORE INSERT ON pedidos
FOR EACH ROW
BEGIN
    IF NEW.cliente_id IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'cliente_id não pode ser nulo';
    END IF;
END$$

CREATE TRIGGER trg_pedido_after_insert
AFTER INSERT ON pedidos
FOR EACH ROW
BEGIN
    INSERT INTO log_acoes (tabela, operacao, dados_json)
    VALUES ('pedidos', 'INSERT', JSON_OBJECT('id', NEW.id, 'cliente_id', NEW.cliente_id));
END$$

CREATE TRIGGER trg_pedido_before_update
BEFORE UPDATE ON pedidos
FOR EACH ROW
BEGIN
    IF OLD.status = 'entregue' AND NEW.status != 'entregue' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Não é possível alterar pedido já entregue';
    END IF;
END$$

CREATE TRIGGER trg_pedido_after_update
AFTER UPDATE ON pedidos
FOR EACH ROW
BEGIN
    INSERT INTO log_acoes (tabela, operacao, dados_json)
    VALUES ('pedidos', 'UPDATE', JSON_OBJECT(
        'id', NEW.id,
        'status_anterior', OLD.status,
        'status_novo', NEW.status
    ));
END$$

CREATE TRIGGER trg_pedido_after_delete
AFTER DELETE ON pedidos
FOR EACH ROW
BEGIN
    INSERT INTO log_acoes (tabela, operacao, dados_json)
    VALUES ('pedidos', 'DELETE', JSON_OBJECT('id', OLD.id, 'cliente_id', OLD.cliente_id));
END$$

CREATE TRIGGER trg_item_after_insert
AFTER INSERT ON pedido_itens
FOR EACH ROW
BEGIN
    UPDATE produtos SET estoque = estoque - NEW.quantidade WHERE id = NEW.produto_id;
    UPDATE pedidos
    SET total = (SELECT SUM(quantidade * preco_unit) FROM pedido_itens WHERE pedido_id = NEW.pedido_id)
    WHERE id = NEW.pedido_id;
END$$

CREATE TRIGGER trg_item_after_delete
AFTER DELETE ON pedido_itens
FOR EACH ROW
BEGIN
    UPDATE produtos SET estoque = estoque + OLD.quantidade WHERE id = OLD.produto_id;
END$$

DELIMITER ;

SHOW TRIGGERS;
DROP TRIGGER IF EXISTS trg_pedido_after_insert;

-- ============================================================
-- TRANSACTIONS
-- ============================================================

START TRANSACTION;
    UPDATE clientes SET saldo = saldo - 500 WHERE id = 1;
    UPDATE clientes SET saldo = saldo + 500 WHERE id = 2;
COMMIT;

START TRANSACTION;
    DELETE FROM pedido_itens WHERE pedido_id = 1;
    DELETE FROM pedidos WHERE id = 1;
ROLLBACK;

START TRANSACTION;
    UPDATE clientes SET saldo = saldo + 100 WHERE id = 1;
    SAVEPOINT sp1;
    UPDATE clientes SET saldo = saldo + 200 WHERE id = 2;
    SAVEPOINT sp2;
    UPDATE clientes SET saldo = saldo + 300 WHERE id = 3;
    ROLLBACK TO SAVEPOINT sp1;
COMMIT;

SET autocommit = 0;
UPDATE clientes SET ativo = FALSE WHERE id = 99;
ROLLBACK;
SET autocommit = 1;

-- ============================================================
-- DCL - DATA CONTROL LANGUAGE
-- ============================================================

CREATE USER IF NOT EXISTS 'loja_user'@'localhost' IDENTIFIED BY 'senha_segura_123';
CREATE USER IF NOT EXISTS 'loja_ro'@'%' IDENTIFIED BY 'somente_leitura';

GRANT ALL PRIVILEGES ON loja.* TO 'loja_user'@'localhost';
GRANT SELECT ON loja.* TO 'loja_ro'@'%';
GRANT SELECT, INSERT ON loja.pedidos   TO 'loja_user'@'localhost';
GRANT EXECUTE         ON loja.*        TO 'loja_user'@'localhost';

REVOKE INSERT ON loja.pedidos FROM 'loja_user'@'localhost';
REVOKE ALL PRIVILEGES ON loja.* FROM 'loja_ro'@'%';

FLUSH PRIVILEGES;
SHOW GRANTS FOR 'loja_user'@'localhost';

DROP USER IF EXISTS 'loja_ro'@'%';

-- ============================================================
-- ANÁLISE E METADATA
-- ============================================================

SHOW DATABASES;
SHOW TABLES;
SHOW FULL TABLES;
SHOW COLUMNS FROM clientes;
SHOW FULL COLUMNS FROM clientes;
SHOW INDEX FROM produtos;
SHOW CREATE TABLE pedidos;
SHOW CREATE VIEW vw_produtos_com_categoria;
SHOW PROCESSLIST;
SHOW STATUS;
SHOW VARIABLES;
SHOW VARIABLES LIKE 'max_connections';
SHOW ENGINES;
SHOW COLLATION;
SHOW CHARACTER SET;

DESCRIBE clientes;
EXPLAIN SELECT * FROM clientes WHERE email = 'ana@email.com';
EXPLAIN FORMAT=JSON SELECT * FROM pedidos WHERE cliente_id = 1;

SELECT * FROM information_schema.TABLES   WHERE TABLE_SCHEMA = 'loja';
SELECT * FROM information_schema.COLUMNS  WHERE TABLE_SCHEMA = 'loja' AND TABLE_NAME = 'clientes';
SELECT * FROM information_schema.INDEXES  WHERE TABLE_SCHEMA = 'loja';
SELECT * FROM information_schema.VIEWS    WHERE TABLE_SCHEMA = 'loja';
SELECT * FROM information_schema.ROUTINES WHERE ROUTINE_SCHEMA = 'loja';
SELECT * FROM information_schema.TRIGGERS WHERE TRIGGER_SCHEMA = 'loja';

ANALYZE TABLE clientes;
OPTIMIZE TABLE pedidos;
CHECK TABLE produtos;
REPAIR TABLE log_acoes;

-- ============================================================
-- SET / USER VARIABLES / SESSION VARIABLES
-- ============================================================

SET @minha_var = 100;
SET @nome      = 'teste';
SET @data      = NOW();

SELECT @minha_var, @nome, @data;

SET @row_num = 0;
SELECT @row_num := @row_num + 1 AS linha, nome FROM clientes;

SET SESSION max_execution_time = 5000;
SET GLOBAL max_connections = 200;

SELECT @@version;
SELECT @@global.max_connections;
SELECT @@session.sql_mode;

-- ============================================================
-- OPERADORES AVANÇADOS
-- ============================================================

SELECT 5 + 3, 10 - 4, 6 * 7, 15 / 4, 15 DIV 4, 15 MOD 4;

SELECT b'1010', 0b1010, x'FF', 0xFF;

SELECT
    5 & 3,
    5 | 3,
    5 ^ 3,
    ~5,
    5 << 1,
    5 >> 1;

SELECT TRUE AND FALSE, TRUE OR FALSE, NOT TRUE, TRUE XOR TRUE;

SELECT 1 <=> NULL, NULL <=> NULL, NULL = NULL;

SELECT * FROM clientes WHERE nome REGEXP '^[A-Z]';
SELECT * FROM clientes WHERE nome REGEXP 'Silva|Costa';
SELECT * FROM produtos WHERE nome RLIKE 'book|livro';

-- ============================================================
-- UNION / INTERSECT / EXCEPT
-- ============================================================

SELECT nome, 'cliente' AS tipo FROM clientes
UNION ALL
SELECT nome, 'produto' AS tipo FROM produtos;

SELECT nome FROM clientes WHERE saldo > 500
UNION
SELECT nome FROM clientes WHERE id IN (SELECT cliente_id FROM pedidos);

-- MySQL 8.0+
SELECT nome FROM clientes
UNION
SELECT nome FROM produtos;

-- ============================================================
-- PIVOT (simulado com CASE)
-- ============================================================

SELECT
    DATE_FORMAT(criado_em, '%Y-%m') AS mes,
    SUM(CASE WHEN status = 'aprovado'  THEN total ELSE 0 END) AS aprovado,
    SUM(CASE WHEN status = 'entregue'  THEN total ELSE 0 END) AS entregue,
    SUM(CASE WHEN status = 'cancelado' THEN total ELSE 0 END) AS cancelado,
    COUNT(*) AS total_pedidos
FROM pedidos
GROUP BY DATE_FORMAT(criado_em, '%Y-%m');

-- ============================================================
-- GERAÇÃO DE DADOS / DATAS EM SÉRIE
-- ============================================================

WITH RECURSIVE datas AS (
    SELECT CURDATE() - INTERVAL 30 DAY AS dt
    UNION ALL
    SELECT dt + INTERVAL 1 DAY FROM datas WHERE dt < CURDATE()
)
SELECT dt FROM datas;

WITH RECURSIVE numeros AS (
    SELECT 1 AS n
    UNION ALL
    SELECT n + 1 FROM numeros WHERE n < 100
)
SELECT n FROM numeros;

-- ============================================================
-- LOCK TABLES
-- ============================================================

LOCK TABLES clientes READ, pedidos WRITE;
UNLOCK TABLES;

SELECT * FROM clientes FOR UPDATE;
SELECT * FROM clientes LOCK IN SHARE MODE;

-- ============================================================
-- LOAD DATA / SELECT INTO OUTFILE
-- ============================================================

-- SELECT * FROM clientes INTO OUTFILE '/tmp/clientes.csv'
--     FIELDS TERMINATED BY ','
--     OPTIONALLY ENCLOSED BY '"'
--     LINES TERMINATED BY '\n';

-- LOAD DATA INFILE '/tmp/clientes.csv'
--     INTO TABLE clientes
--     FIELDS TERMINATED BY ','
--     OPTIONALLY ENCLOSED BY '"'
--     LINES TERMINATED BY '\n'
--     IGNORE 1 ROWS;

-- ============================================================
-- PREPARED STATEMENTS
-- ============================================================

PREPARE stmt_busca FROM 'SELECT * FROM clientes WHERE id = ?';
SET @id_busca = 1;
EXECUTE stmt_busca USING @id_busca;
DEALLOCATE PREPARE stmt_busca;

PREPARE stmt_insert FROM 'INSERT INTO log_acoes (tabela, operacao) VALUES (?, ?)';
SET @tb = 'clientes';
SET @op = 'TEST';
EXECUTE stmt_insert USING @tb, @op;
DEALLOCATE PREPARE stmt_insert;

-- ============================================================
-- EVENTOS (EVENT SCHEDULER)
-- ============================================================

SET GLOBAL event_scheduler = ON;

DELIMITER $$

CREATE EVENT IF NOT EXISTS ev_limpar_logs
ON SCHEDULE EVERY 1 WEEK
STARTS '2025-01-01 00:00:00'
DO
BEGIN
    DELETE FROM log_acoes WHERE momento < DATE_SUB(NOW(), INTERVAL 90 DAY);
END$$

CREATE EVENT IF NOT EXISTS ev_relatorio_diario
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_TIMESTAMP + INTERVAL 1 DAY
DO
BEGIN
    INSERT INTO log_acoes (tabela, operacao, dados_json)
    SELECT 'pedidos', 'DAILY_REPORT', JSON_OBJECT('data', CURDATE(), 'total', COUNT(*))
    FROM pedidos WHERE DATE(criado_em) = CURDATE();
END$$

DELIMITER ;

SHOW EVENTS;
ALTER EVENT ev_limpar_logs DISABLE;
ALTER EVENT ev_limpar_logs ENABLE;
DROP EVENT IF EXISTS ev_limpar_logs;

-- ============================================================
-- PARTICIONAMENTO DE TABELAS
-- ============================================================

CREATE TABLE pedidos_particionados (
    id         INT            NOT NULL AUTO_INCREMENT,
    cliente_id INT            NOT NULL,
    total      DECIMAL(10,2),
    status     VARCHAR(20),
    criado_em  DATE           NOT NULL,
    PRIMARY KEY (id, criado_em)
)
PARTITION BY RANGE (YEAR(criado_em)) (
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p_max VALUES LESS THAN MAXVALUE
);

CREATE TABLE clientes_particionados (
    id   INT          NOT NULL,
    nome VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
)
PARTITION BY HASH(id)
PARTITIONS 4;

ALTER TABLE pedidos_particionados ADD PARTITION (PARTITION p2026 VALUES LESS THAN (2027));
ALTER TABLE pedidos_particionados DROP PARTITION p2023;

SELECT PARTITION_NAME, TABLE_ROWS
FROM information_schema.PARTITIONS
WHERE TABLE_SCHEMA = 'loja' AND TABLE_NAME = 'pedidos_particionados';

-- ============================================================
-- FULL-TEXT SEARCH
-- ============================================================

ALTER TABLE produtos ADD FULLTEXT ft_busca (nome, descricao);

SELECT nome, descricao,
    MATCH(nome, descricao) AGAINST ('notebook laptop' IN NATURAL LANGUAGE MODE) AS relevancia
FROM produtos
WHERE MATCH(nome, descricao) AGAINST ('notebook laptop' IN NATURAL LANGUAGE MODE);

SELECT nome FROM produtos
WHERE MATCH(nome, descricao) AGAINST ('+notebook -usado' IN BOOLEAN MODE);

SELECT nome FROM produtos
WHERE MATCH(nome, descricao) AGAINST ('"notebook 15"' IN BOOLEAN MODE);

SELECT nome FROM produtos
WHERE MATCH(nome, descricao) AGAINST ('notebook' WITH QUERY EXPANSION);

-- ============================================================
-- QUERIES ANALÍTICAS AVANÇADAS
-- ============================================================

SELECT
    cl.nome,
    pe.total,
    AVG(pe.total) OVER () AS media_geral,
    pe.total - AVG(pe.total) OVER () AS desvio_da_media,
    SUM(pe.total) OVER (PARTITION BY pe.cliente_id ORDER BY pe.criado_em
        ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) AS media_movel_3
FROM pedidos pe
JOIN clientes cl ON pe.cliente_id = cl.id;

SELECT
    nome,
    preco,
    NTILE(3) OVER (ORDER BY preco) AS tercil,
    PERCENT_RANK() OVER (ORDER BY preco) AS percentil
FROM produtos;

WITH crescimento AS (
    SELECT
        DATE_FORMAT(criado_em, '%Y-%m') AS mes,
        SUM(total) AS faturamento
    FROM pedidos
    WHERE status != 'cancelado'
    GROUP BY mes
)
SELECT
    mes,
    faturamento,
    LAG(faturamento) OVER (ORDER BY mes) AS mes_anterior,
    ROUND(
        (faturamento - LAG(faturamento) OVER (ORDER BY mes))
        / LAG(faturamento) OVER (ORDER BY mes) * 100,
    2) AS crescimento_pct
FROM crescimento;

WITH ranking_produtos AS (
    SELECT
        pr.id,
        pr.nome,
        SUM(pi.quantidade * pi.preco_unit) AS receita,
        RANK() OVER (ORDER BY SUM(pi.quantidade * pi.preco_unit) DESC) AS rnk
    FROM produtos pr
    JOIN pedido_itens pi ON pr.id = pi.produto_id
    GROUP BY pr.id, pr.nome
)
SELECT * FROM ranking_produtos WHERE rnk <= 3;

-- ============================================================
-- LIMPEZA FINAL
-- ============================================================

DROP TABLE IF EXISTS tmp_resumo;
DROP TABLE IF EXISTS pedidos_particionados;
DROP TABLE IF EXISTS clientes_particionados;

SELECT 'Script SQL completo executado com sucesso!' AS status;

-- ============================================================
-- MODELO FÍSICO - STORAGE ENGINES
-- ============================================================

CREATE TABLE tb_innodb (
    id INT PRIMARY KEY
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC;

CREATE TABLE tb_myisam (
    id INT PRIMARY KEY
) ENGINE = MyISAM;

CREATE TABLE tb_memory (
    id   INT PRIMARY KEY,
    dado VARCHAR(100)
) ENGINE = MEMORY;

CREATE TABLE tb_archive (
    id        INT       NOT NULL,
    evento    TEXT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE = ARCHIVE;

ALTER TABLE clientes ENGINE = InnoDB;

SHOW TABLE STATUS FROM loja;
SHOW TABLE STATUS FROM loja LIKE 'clientes';

-- ============================================================
-- MODELO FÍSICO - TIPOS DE DADOS COMPLETOS
-- ============================================================

CREATE TABLE tipos_numericos (
    col_tinyint    TINYINT,
    col_smallint   SMALLINT,
    col_mediumint  MEDIUMINT,
    col_int        INT,
    col_bigint     BIGINT,
    col_tinyint_u  TINYINT  UNSIGNED,
    col_bigint_u   BIGINT   UNSIGNED,
    col_decimal    DECIMAL(15, 4),
    col_numeric    NUMERIC(10, 2),
    col_float      FLOAT,
    col_double     DOUBLE,
    col_bit        BIT(8)
);

CREATE TABLE tipos_string (
    col_char       CHAR(10),
    col_varchar    VARCHAR(255),
    col_tinytext   TINYTEXT,
    col_text       TEXT,
    col_mediumtext MEDIUMTEXT,
    col_longtext   LONGTEXT,
    col_binary     BINARY(16),
    col_varbinary  VARBINARY(255),
    col_tinyblob   TINYBLOB,
    col_blob       BLOB,
    col_mediumblob MEDIUMBLOB,
    col_longblob   LONGBLOB,
    col_enum       ENUM('A', 'B', 'C'),
    col_set        SET('leitura', 'escrita', 'execucao')
);

CREATE TABLE tipos_data_hora (
    col_date      DATE,
    col_time      TIME,
    col_datetime  DATETIME,
    col_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    col_year      YEAR
);

CREATE TABLE tipos_espaciais (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    ponto  POINT         NOT NULL,
    linha  LINESTRING,
    polig  POLYGON,
    geom   GEOMETRY,
    multi_p MULTIPOINT,
    multi_l MULTILINESTRING,
    multi_g MULTIPOLYGON
) ENGINE = InnoDB;

CREATE TABLE tipos_json (
    id   INT PRIMARY KEY AUTO_INCREMENT,
    dado JSON
);

-- ============================================================
-- MODELO FÍSICO - NORMALIZAÇÃO (1FN → BCFN)
-- ============================================================

-- VIOLAÇÃO 1FN (dado multivalorado na mesma coluna)
CREATE TABLE pedidos_desnorm (
    id       INT PRIMARY KEY,
    cliente  VARCHAR(100),
    produtos TEXT
);

-- 1FN: atomicidade
CREATE TABLE pedidos_1fn (
    id         INT PRIMARY KEY,
    cliente_id INT,
    produto_id INT,
    quantidade INT
);

-- VIOLAÇÃO 2FN (dependência parcial da chave composta)
CREATE TABLE pedido_prod_desnorm (
    pedido_id    INT,
    produto_id   INT,
    nome_produto VARCHAR(100),
    quantidade   INT,
    PRIMARY KEY (pedido_id, produto_id)
);

-- 2FN: separar o que depende só de produto_id
CREATE TABLE pedido_prod_2fn (
    pedido_id  INT,
    produto_id INT,
    quantidade INT,
    PRIMARY KEY (pedido_id, produto_id)
);

CREATE TABLE produto_2fn (
    id   INT PRIMARY KEY,
    nome VARCHAR(100)
);

-- VIOLAÇÃO 3FN (dependência transitiva)
CREATE TABLE funcionarios_desnorm (
    id           INT PRIMARY KEY,
    nome         VARCHAR(100),
    depto_id     INT,
    depto_nome   VARCHAR(100),
    depto_gerente VARCHAR(100)
);

-- 3FN: separar departamento
CREATE TABLE departamentos_3fn (
    id      INT PRIMARY KEY,
    nome    VARCHAR(100),
    gerente VARCHAR(100)
);

CREATE TABLE funcionarios_3fn (
    id       INT PRIMARY KEY,
    nome     VARCHAR(100),
    depto_id INT,
    FOREIGN KEY (depto_id) REFERENCES departamentos_3fn (id)
);

-- BCNF (Boyce-Codd)
-- ex: professor -> disciplina -> sala, professor -> sala (anomalia)
CREATE TABLE alocacao_bcnf (
    professor  VARCHAR(100),
    disciplina VARCHAR(100),
    sala       VARCHAR(20),
    PRIMARY KEY (professor, disciplina)
);

-- ============================================================
-- MODELO FÍSICO - PADRÕES DE CHAVE
-- ============================================================

-- Chave natural
CREATE TABLE paises (
    codigo_iso CHAR(2)     PRIMARY KEY,
    nome       VARCHAR(80) NOT NULL
);

-- Chave surrogate (artificial)
CREATE TABLE paises_surrogate (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    codigo_iso CHAR(2)     UNIQUE NOT NULL,
    nome       VARCHAR(80) NOT NULL
);

-- Chave composta
CREATE TABLE matriculas (
    aluno_id    INT,
    disciplina_id INT,
    semestre    CHAR(6),
    nota        DECIMAL(4,2),
    PRIMARY KEY (aluno_id, disciplina_id, semestre)
);

-- UUID como chave
CREATE TABLE sessoes (
    id         CHAR(36)     DEFAULT (UUID()) PRIMARY KEY,
    usuario_id INT          NOT NULL,
    criado_em  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Chave natural composta
CREATE TABLE estoque_loja (
    loja_id    INT,
    produto_id INT,
    quantidade INT DEFAULT 0,
    PRIMARY KEY (loja_id, produto_id)
);

-- ============================================================
-- MODELO FÍSICO - ESTRATÉGIAS DE ÍNDICE
-- ============================================================

-- Índice simples
CREATE INDEX idx_prod_preco ON produtos (preco);

-- Índice composto (ordem importa para o query planner)
CREATE INDEX idx_ped_cli_status ON pedidos (cliente_id, status);

-- Índice composto invertido
CREATE INDEX idx_ped_status_cli ON pedidos (status, cliente_id);

-- Índice de cobertura (covering index)
CREATE INDEX idx_cobertura_pedido
    ON pedido_itens (pedido_id, produto_id, quantidade, preco_unit);

-- Índice em expressão / coluna gerada
ALTER TABLE clientes ADD COLUMN nome_upper VARCHAR(150)
    GENERATED ALWAYS AS (UPPER(nome)) STORED;
CREATE INDEX idx_nome_upper ON clientes (nome_upper);

-- Índice prefixo (para texto longo)
CREATE INDEX idx_prod_desc_prefix ON produtos (descricao(50));

-- Índice descendente (MySQL 8+)
CREATE INDEX idx_saldo_desc ON clientes (saldo DESC);

-- Índice invisível (MySQL 8+) — existe mas o optimizer ignora
CREATE INDEX idx_invisivel ON produtos (peso);
ALTER TABLE produtos ALTER INDEX idx_invisivel INVISIBLE;
ALTER TABLE produtos ALTER INDEX idx_invisivel VISIBLE;

-- Hints de índice
SELECT * FROM produtos USE INDEX (idx_prod_preco) WHERE preco > 100;
SELECT * FROM produtos FORCE INDEX (idx_prod_preco) WHERE preco > 100;
SELECT * FROM produtos IGNORE INDEX (idx_prod_preco) WHERE preco > 100;

-- Verificar uso de índice
SHOW INDEX FROM produtos;

-- Cardinality vs seletividade
SELECT
    index_name,
    cardinality,
    cardinality / (SELECT COUNT(*) FROM clientes) AS seletividade
FROM information_schema.STATISTICS
WHERE table_schema = 'loja' AND table_name = 'clientes';

-- ============================================================
-- MODELO FÍSICO - HERANÇA DE TABELA (PADRÕES)
-- ============================================================

-- Single Table Inheritance
CREATE TABLE pessoas_sti (
    id       INT          PRIMARY KEY AUTO_INCREMENT,
    tipo     ENUM('pf', 'pj') NOT NULL,
    nome     VARCHAR(150) NOT NULL,
    cpf      CHAR(11),
    cnpj     CHAR(14),
    email    VARCHAR(150)
);

-- Class Table Inheritance
CREATE TABLE pessoas_cti (
    id    INT PRIMARY KEY AUTO_INCREMENT,
    nome  VARCHAR(150) NOT NULL,
    email VARCHAR(150)
);

CREATE TABLE pessoas_fisicas (
    pessoa_id INT PRIMARY KEY,
    cpf       CHAR(11) UNIQUE NOT NULL,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas_cti (id) ON DELETE CASCADE
);

CREATE TABLE pessoas_juridicas (
    pessoa_id    INT PRIMARY KEY,
    cnpj         CHAR(14) UNIQUE NOT NULL,
    razao_social VARCHAR(200),
    FOREIGN KEY (pessoa_id) REFERENCES pessoas_cti (id) ON DELETE CASCADE
);

-- Concrete Table Inheritance
CREATE TABLE pf_concreta (
    id    INT PRIMARY KEY AUTO_INCREMENT,
    nome  VARCHAR(150) NOT NULL,
    cpf   CHAR(11)     UNIQUE NOT NULL,
    email VARCHAR(150)
);

CREATE TABLE pj_concreta (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    razao_social VARCHAR(200) NOT NULL,
    cnpj         CHAR(14)     UNIQUE NOT NULL,
    email        VARCHAR(150)
);

-- ============================================================
-- MODELO FÍSICO - PADRÕES DE AUDITORIA
-- ============================================================

-- Soft delete
ALTER TABLE produtos ADD COLUMN deletado_em TIMESTAMP NULL DEFAULT NULL;
UPDATE produtos SET deletado_em = NOW() WHERE id = 99;
SELECT * FROM produtos WHERE deletado_em IS NULL;

-- Tabela de histórico (temporal)
CREATE TABLE clientes_historico (
    hist_id       INT          NOT NULL AUTO_INCREMENT,
    cliente_id    INT          NOT NULL,
    campo         VARCHAR(80)  NOT NULL,
    valor_antigo  TEXT,
    valor_novo    TEXT,
    alterado_por  VARCHAR(100) DEFAULT CURRENT_USER(),
    alterado_em   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (hist_id)
);

-- System-versioned (SQL:2011 / MariaDB)
-- CREATE TABLE clientes_temporal (
--     id    INT PRIMARY KEY,
--     nome  VARCHAR(100)
-- ) WITH SYSTEM VERSIONING;

-- Tabela de auditoria genérica com JSON
CREATE TABLE auditoria (
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    schema_nome VARCHAR(80),
    tabela_nome VARCHAR(80),
    operacao    ENUM('INSERT', 'UPDATE', 'DELETE'),
    pk_valor    VARCHAR(255),
    antes       JSON,
    depois      JSON,
    usuario     VARCHAR(100)  DEFAULT CURRENT_USER(),
    ip          VARCHAR(45),
    momento     TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id)
);

-- ============================================================
-- MODELO FÍSICO - DESNORMALIZAÇÃO E PERFORMANCE
-- ============================================================

-- Coluna calculada armazenada (evita JOIN ou subquery frequente)
ALTER TABLE pedidos ADD COLUMN qtd_itens INT DEFAULT 0;

DELIMITER $$
CREATE TRIGGER trg_atualiza_qtd_itens
AFTER INSERT ON pedido_itens
FOR EACH ROW
BEGIN
    UPDATE pedidos
    SET qtd_itens = (SELECT COUNT(*) FROM pedido_itens WHERE pedido_id = NEW.pedido_id)
    WHERE id = NEW.pedido_id;
END$$
DELIMITER ;

-- Tabela de resumo (materialized view manual)
CREATE TABLE resumo_vendas_diario (
    data          DATE           NOT NULL,
    total_pedidos INT            DEFAULT 0,
    faturamento   DECIMAL(14,2)  DEFAULT 0.00,
    atualizado_em TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (data)
);

-- ============================================================
-- MODELO FÍSICO - RELACIONAMENTOS AVANÇADOS
-- ============================================================

-- Auto-relacionamento (hierarquia)
CREATE TABLE categorias_hierarquia (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    nome      VARCHAR(100) NOT NULL,
    pai_id    INT,
    FOREIGN KEY (pai_id) REFERENCES categorias_hierarquia (id)
);

INSERT INTO categorias_hierarquia (nome, pai_id) VALUES
    ('Tecnologia',   NULL),
    ('Hardware',     1),
    ('Software',     1),
    ('Processadores',2),
    ('Memória',      2);

WITH RECURSIVE arvore (id, nome, pai_id, nivel, caminho) AS (
    SELECT id, nome, pai_id, 0, CAST(nome AS CHAR(500))
    FROM categorias_hierarquia WHERE pai_id IS NULL
    UNION ALL
    SELECT c.id, c.nome, c.pai_id, a.nivel + 1,
           CONCAT(a.caminho, ' > ', c.nome)
    FROM categorias_hierarquia c
    JOIN arvore a ON c.pai_id = a.id
)
SELECT * FROM arvore ORDER BY caminho;

-- Relacionamento N:N explícito com atributos
CREATE TABLE alunos (id INT PRIMARY KEY, nome VARCHAR(100));
CREATE TABLE cursos  (id INT PRIMARY KEY, nome VARCHAR(100));

CREATE TABLE inscricoes (
    aluno_id   INT,
    curso_id   INT,
    inscrito_em DATE,
    nota_final  DECIMAL(4,2),
    PRIMARY KEY (aluno_id, curso_id),
    FOREIGN KEY (aluno_id) REFERENCES alunos (id),
    FOREIGN KEY (curso_id) REFERENCES cursos  (id)
);

-- Relacionamento polimórfico
CREATE TABLE comentarios (
    id            INT          PRIMARY KEY AUTO_INCREMENT,
    entidade_tipo VARCHAR(50)  NOT NULL,
    entidade_id   INT          NOT NULL,
    texto         TEXT,
    criado_em     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_entidade (entidade_tipo, entidade_id)
);

INSERT INTO comentarios (entidade_tipo, entidade_id, texto)
VALUES ('produto', 1, 'Ótimo produto'), ('pedido', 2, 'Entregou rápido');

SELECT * FROM comentarios WHERE entidade_tipo = 'produto' AND entidade_id = 1;

-- ============================================================
-- UPSERT / MERGE
-- ============================================================

INSERT INTO resumo_vendas_diario (data, total_pedidos, faturamento)
VALUES (CURDATE(), 1, 500.00)
ON DUPLICATE KEY UPDATE
    total_pedidos = total_pedidos + VALUES(total_pedidos),
    faturamento   = faturamento   + VALUES(faturamento);

REPLACE INTO resumo_vendas_diario (data, total_pedidos, faturamento)
VALUES (CURDATE(), 5, 2000.00);

INSERT INTO resumo_vendas_diario (data, total_pedidos, faturamento)
VALUES (CURDATE(), 1, 300.00)
ON DUPLICATE KEY UPDATE
    total_pedidos = total_pedidos + 1,
    faturamento   = faturamento   + 300.00;

-- ============================================================
-- EXPLAIN / QUERY OPTIMIZER
-- ============================================================

EXPLAIN SELECT * FROM clientes WHERE email = 'ana@email.com';

EXPLAIN FORMAT = JSON
SELECT p.nome, c.nome AS categoria
FROM produtos p
JOIN categorias c ON p.categoria_id = c.id
WHERE p.preco > 100;

EXPLAIN ANALYZE
SELECT cl.nome, SUM(pe.total)
FROM clientes cl
JOIN pedidos pe ON cl.id = pe.cliente_id
GROUP BY cl.id;

-- Optimizer hints (MySQL 8+)
SELECT /*+ NO_INDEX(clientes idx_cliente_email) */ * FROM clientes;
SELECT /*+ MAX_EXECUTION_TIME(1000) */ * FROM pedidos;
SELECT /*+ JOIN_ORDER(pe, cl) */ cl.nome, pe.total
FROM pedidos pe JOIN clientes cl ON pe.cliente_id = cl.id;

-- ============================================================
-- CHARSET / COLLATION
-- ============================================================

CREATE DATABASE loja_utf8
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

CREATE TABLE mensagens (
    id      INT PRIMARY KEY AUTO_INCREMENT,
    texto   VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    emoji   VARCHAR(100) CHARACTER SET utf8mb4
);

SELECT * FROM clientes WHERE nome COLLATE utf8mb4_general_ci = 'ana silva';

SHOW CHARACTER SET;
SHOW COLLATION WHERE Charset = 'utf8mb4';

SELECT
    CHARSET(nome),
    COLLATION(nome)
FROM clientes LIMIT 1;

CONVERT('texto' USING utf8mb4);
SELECT CONVERT(nome USING latin1) FROM clientes LIMIT 1;

-- ============================================================
-- COLUNAS GERADAS (GENERATED COLUMNS)
-- ============================================================

CREATE TABLE produtos_gen (
    id          INT            PRIMARY KEY AUTO_INCREMENT,
    preco       DECIMAL(10,2)  NOT NULL,
    desconto    DECIMAL(5,2)   DEFAULT 0,
    preco_final DECIMAL(10,2)  GENERATED ALWAYS AS (ROUND(preco * (1 - desconto / 100), 2)) STORED,
    margem_text VARCHAR(20)    GENERATED ALWAYS AS (
        CASE
            WHEN preco < 50   THEN 'baixo'
            WHEN preco < 200  THEN 'medio'
            ELSE 'alto'
        END
    ) VIRTUAL
);

INSERT INTO produtos_gen (preco, desconto) VALUES (100.00, 10), (500.00, 5);
SELECT * FROM produtos_gen;

-- ============================================================
-- TABLESPACES (InnoDB)
-- ============================================================

-- CREATE TABLESPACE ts_loja ADD DATAFILE 'ts_loja.ibd' ENGINE = InnoDB;
-- CREATE TABLE tb_com_tablespace (...) TABLESPACE ts_loja;

-- File-per-table (padrão innodb_file_per_table = ON)
SHOW VARIABLES LIKE 'innodb_file_per_table';
SHOW VARIABLES LIKE 'innodb_page_size';
SHOW VARIABLES LIKE 'innodb_buffer_pool_size';
SHOW VARIABLES LIKE 'innodb_log_file_size';

-- ============================================================
-- PADRÕES AVANÇADOS DE QUERY
-- ============================================================

-- Paginação eficiente com keyset (evita OFFSET grande)
SELECT id, nome, preco FROM produtos WHERE id > 5 ORDER BY id LIMIT 10;

-- Gap and Islands (identificar sequências)
WITH numerados AS (
    SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS rn FROM clientes
),
gaps AS (
    SELECT id, id - rn AS grp FROM numerados
)
SELECT MIN(id) AS inicio, MAX(id) AS fim, COUNT(*) AS tamanho
FROM gaps GROUP BY grp;

-- Running total
SELECT
    id,
    total,
    SUM(total) OVER (ORDER BY criado_em ROWS UNBOUNDED PRECEDING) AS acumulado
FROM pedidos WHERE total IS NOT NULL;

-- Top N por grupo
WITH ranked AS (
    SELECT *, RANK() OVER (PARTITION BY categoria_id ORDER BY preco DESC) AS rnk
    FROM produtos
)
SELECT * FROM ranked WHERE rnk <= 2;

-- Deduplicação (manter apenas a última linha duplicada)
DELETE p1 FROM clientes p1
INNER JOIN clientes p2
WHERE p1.email = p2.email AND p1.id < p2.id;

-- Encontrar duplicatas
SELECT email, COUNT(*) AS qtd
FROM clientes
GROUP BY email
HAVING COUNT(*) > 1;

-- Pivot dinâmico simulado com GROUP_CONCAT
SET @cols = NULL;
SELECT GROUP_CONCAT(DISTINCT CONCAT("SUM(CASE WHEN status='", status, "' THEN total ELSE 0 END) AS `", status, "`"))
INTO @cols
FROM pedidos;

SET @query = CONCAT('SELECT cliente_id, ', @cols, ' FROM pedidos GROUP BY cliente_id');
PREPARE stmt_pivot FROM @query;
EXECUTE stmt_pivot;
DEALLOCATE PREPARE stmt_pivot;

-- ============================================================
-- LATERAL JOIN (MySQL 8.0.14+)
-- ============================================================

SELECT cl.nome, ultimos.id AS ultimo_pedido_id, ultimos.total
FROM clientes cl
JOIN LATERAL (
    SELECT id, total FROM pedidos
    WHERE cliente_id = cl.id
    ORDER BY criado_em DESC LIMIT 1
) ultimos ON TRUE;

-- ============================================================
-- GROUPING SETS / CUBE / ROLLUP COMPLETO
-- ============================================================

SELECT
    categoria_id,
    CASE WHEN ativo = 1 THEN 'ativo' ELSE 'inativo' END AS status,
    COUNT(*) AS total,
    SUM(preco) AS soma
FROM produtos
GROUP BY categoria_id, ativo WITH ROLLUP;

SELECT
    GROUPING(categoria_id) AS is_total_categoria,
    categoria_id,
    COUNT(*) AS total
FROM produtos
GROUP BY categoria_id WITH ROLLUP;

-- ============================================================
-- WINDOW FRAME AVANÇADO
-- ============================================================

SELECT
    id,
    preco,
    AVG(preco) OVER (
        ORDER BY id
        ROWS BETWEEN 2 PRECEDING AND 2 FOLLOWING
    ) AS media_movel_5,
    SUM(preco) OVER (
        ORDER BY id
        RANGE BETWEEN INTERVAL 100 FOLLOWING AND INTERVAL 500 FOLLOWING
    ) AS soma_range,
    FIRST_VALUE(preco) OVER (
        PARTITION BY categoria_id
        ORDER BY preco
        ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
    ) AS menor_da_categoria
FROM produtos;

-- Named window (reutilizar definição)
SELECT
    id, preco,
    SUM(preco)  OVER w AS soma,
    AVG(preco)  OVER w AS media,
    COUNT(*)    OVER w AS contagem
FROM produtos
WINDOW w AS (PARTITION BY categoria_id ORDER BY preco);

-- ============================================================
-- FUNÇÕES ESPACIAIS (GIS)
-- ============================================================

INSERT INTO tipos_espaciais (ponto, linha, polig) VALUES (
    ST_GeomFromText('POINT(23.5505 46.6333)'),
    ST_GeomFromText('LINESTRING(0 0, 10 10, 20 0)'),
    ST_GeomFromText('POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))')
);

SELECT
    ST_AsText(ponto),
    ST_X(ponto) AS longitude,
    ST_Y(ponto) AS latitude,
    ST_Distance(
        ST_GeomFromText('POINT(0 0)'),
        ST_GeomFromText('POINT(3 4)')
    ) AS distancia,
    ST_Contains(
        ST_GeomFromText('POLYGON((0 0, 10 0, 10 10, 0 10, 0 0))'),
        ST_GeomFromText('POINT(5 5)')
    ) AS ponto_dentro,
    ST_Intersects(
        ST_GeomFromText('LINESTRING(0 0, 10 10)'),
        ST_GeomFromText('LINESTRING(0 10, 10 0)')
    ) AS se_cruzam
FROM tipos_espaciais LIMIT 1;

-- ============================================================
-- REPLICAÇÃO - CONCEITOS DDL
-- ============================================================

SHOW MASTER STATUS;
SHOW BINARY LOGS;
SHOW BINLOG EVENTS;
SHOW REPLICA STATUS;

SHOW VARIABLES LIKE 'binlog_format';
SHOW VARIABLES LIKE 'server_id';

-- ============================================================
-- BACKUP / RESTORE (referência de comandos externos)
-- ============================================================

-- mysqldump loja > backup.sql
-- mysqldump loja clientes > backup_clientes.sql
-- mysqldump --single-transaction loja > backup_transacional.sql
-- mysql loja < backup.sql

-- mysqlpump loja > backup_pump.sql
-- xtrabackup --backup --target-dir=/backup

-- ============================================================
-- PERFORMANCE SCHEMA
-- ============================================================

SELECT * FROM performance_schema.events_statements_summary_by_digest
ORDER BY sum_timer_wait DESC LIMIT 10;

SELECT * FROM performance_schema.table_io_waits_summary_by_table
WHERE object_schema = 'loja';

SELECT * FROM performance_schema.memory_summary_global_by_event_name
WHERE event_name LIKE '%innodb%'
ORDER BY current_alloc DESC LIMIT 10;

SELECT * FROM sys.statements_with_full_table_scans LIMIT 10;
SELECT * FROM sys.schema_unused_indexes   WHERE object_schema = 'loja';
SELECT * FROM sys.schema_redundant_indexes WHERE table_schema = 'loja';
SELECT * FROM sys.innodb_buffer_stats_by_table WHERE object_schema = 'loja';

-- ============================================================
-- LIMPEZA FINAL
-- ============================================================

DROP TABLE IF EXISTS tipos_numericos, tipos_string, tipos_data_hora, tipos_espaciais, tipos_json;
DROP TABLE IF EXISTS pedidos_desnorm, pedido_prod_desnorm, pedidos_1fn, pedido_prod_2fn, produto_2fn;
DROP TABLE IF EXISTS funcionarios_desnorm, departamentos_3fn, funcionarios_3fn, alocacao_bcnf;
DROP TABLE IF EXISTS paises, paises_surrogate, sessoes, estoque_loja, matriculas;
DROP TABLE IF EXISTS pessoas_sti, pessoas_cti, pessoas_fisicas, pessoas_juridicas, pf_concreta, pj_concreta;
DROP TABLE IF EXISTS categorias_hierarquia, alunos, cursos, inscricoes, comentarios;
DROP TABLE IF EXISTS clientes_historico, auditoria, resumo_vendas_diario, produtos_gen, mensagens;
DROP TABLE IF EXISTS tb_innodb, tb_myisam, tb_memory, tb_archive;

SELECT 'Arquivo SQL completo — DDL, DML, DCL, TCL, Modelo Físico.' AS status;