-- Drop tables if they exist (start with the ones that don't have dependencies)
DROP TABLE IF EXISTS card_status CASCADE;
DROP TABLE IF EXISTS payment_system CASCADE;
DROP TABLE IF EXISTS issuing_bank CASCADE;
DROP TABLE IF EXISTS acquiring_bank CASCADE;
DROP TABLE IF EXISTS currency CASCADE;
DROP TABLE IF EXISTS merchant_category_code CASCADE;
DROP TABLE IF EXISTS transaction_type CASCADE;
DROP TABLE IF EXISTS response_code CASCADE;
DROP TABLE IF EXISTS terminal CASCADE; -- Drop terminal before account and card due to FK
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS transaction CASCADE;
DROP TABLE IF EXISTS sales_point CASCADE;

-- Create the tables
-- 1 Назначение: Определяет текущий статус карты в процессинговой системе.
CREATE TABLE card_status
(
    id               BIGINT PRIMARY KEY,
    card_status_name VARCHAR(255)
);

-- 2 Назначение: Хранит информацию о поддерживаемых платёжных системах.
CREATE TABLE payment_system
(
    id                  BIGINT PRIMARY KEY,
    payment_system_name VARCHAR(50)
);

-- 3 Назначение: Содержит данные о валютах, используемых в транзакциях.
CREATE TABLE currency
(
    id                    BIGINT PRIMARY KEY,
    currency_digital_code VARCHAR(3),
    currency_letter_code  VARCHAR(3),
    currency_name         VARCHAR(255)
);

-- 4 Назначение: Хранит информацию о банках, выпускающих карты.
CREATE TABLE issuing_bank
(
    id               BIGINT PRIMARY KEY,
    bic              VARCHAR(9),
    bin              VARCHAR(5),
    abbreviated_name VARCHAR(255)
);

-- 5 Назначение: Информация о банках, обрабатывающих платежи через POSтерминалы
CREATE TABLE acquiring_bank
(
    id               BIGINT PRIMARY KEY,
    bic              VARCHAR(9),
    abbreviated_name VARCHAR(255)
);

-- 6 Назначение: Описывает места, где проводятся транзакции.
CREATE TABLE sales_point
(
    id                BIGINT PRIMARY KEY,
    pos_name          VARCHAR(255),
    pos_address       VARCHAR(255),
    pos_inn           VARCHAR(12),
    acquiring_bank_id BIGINT,
    FOREIGN KEY (acquiring_bank_id) REFERENCES acquiring_bank (id)
);

-- 7 Назначение: Хранит коды категорий, которые характеризуют тип точки продаж.
CREATE TABLE merchant_category_code
(
    id       BIGINT PRIMARY KEY,
    mcc      VARCHAR(4),
    mcc_name VARCHAR(255)
);

-- 8 Назначение: Представляет данные о терминалах, использующихся для транзакций.
CREATE TABLE terminal
(
    id          BIGINT PRIMARY KEY,
    terminal_id VARCHAR(9),
    mcc_id      INTEGER REFERENCES merchant_category_code (id),
    pos_id      BIGINT REFERENCES sales_point (id)
);

-- 9 Назначение: Хранит данные о результатах транзакций.
CREATE TABLE response_code
(
    id                BIGINT PRIMARY KEY,
    error_code        VARCHAR(5),
    error_description VARCHAR(255),
    error_level       VARCHAR(255)
);

-- 10 Назначение: Описывает возможные типы операций.
CREATE TABLE transaction_type
(
    id                    BIGINT PRIMARY KEY,
    transaction_type_name VARCHAR(255),
    operator              VARCHAR(1)
);

-- 11 Назначение: Управляет счётами пользователей.
CREATE TABLE account
(
    id              BIGINT PRIMARY KEY,
    account_number  VARCHAR(50),
    balance         DOUBLE PRECISION,
    currency_id     BIGINT,
    issuing_bank_id BIGINT,
    FOREIGN KEY (currency_id) REFERENCES currency (id),
    FOREIGN KEY (issuing_bank_id) REFERENCES issuing_bank (id)
);

-- 12 Назначение: Хранит данные о картах.
CREATE TABLE card
(
    id                         BIGINT PRIMARY KEY,
    card_number                VARCHAR(50),
    expiration_date            DATE,
    holder_name                VARCHAR(50),
    card_status_id             BIGINT,
    payment_system_id          BIGINT,
    account_id                 BIGINT,
    received_from_issuing_bank TIMESTAMP,
    sent_to_issuing_bank       TIMESTAMP,
    FOREIGN KEY (card_status_id) REFERENCES card_status (id),
    FOREIGN KEY (payment_system_id) REFERENCES payment_system (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
);

-- 13 Назначение: Учитывает данные о транзакциях.
CREATE TABLE transaction
(
    id                         BIGINT PRIMARY KEY,
    transaction_date           DATE,
    sum                        DOUBLE PRECISION,
    transaction_name           VARCHAR(255),
    account_id                 BIGINT,
    transaction_type_id        BIGINT,
    card_id                    BIGINT,
    terminal_id                BIGINT,
    response_code_id           BIGINT,
    authorization_code         VARCHAR(6),
    received_from_issuing_bank TIMESTAMP,
    sent_to_issuing_bank       TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account (id),
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_type (id),
    FOREIGN KEY (card_id) REFERENCES card (id),
    FOREIGN KEY (terminal_id) REFERENCES terminal (id),
    FOREIGN KEY (response_code_id) REFERENCES response_code (id)
);

--  add foreign key after creating tables.
ALTER TABLE Terminal
    ADD CONSTRAINT FK_Terminal_SalesPoint FOREIGN KEY (pos_id) REFERENCES sales_point (id);