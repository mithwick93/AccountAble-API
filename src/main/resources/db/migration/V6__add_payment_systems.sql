CREATE TABLE payment_system_types
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE payment_systems
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    type_id     INT          NOT NULL,
    currency_id INT          NOT NULL,
    user_id     INT          NOT NULL,
    FOREIGN KEY (type_id) REFERENCES payment_system_types (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE payment_system_debits
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    asset_id    INT NOT NULL,
    daily_limit DECIMAL(19, 4),
    FOREIGN KEY (id) REFERENCES payment_systems (id),
    FOREIGN KEY (asset_id) REFERENCES assets (id)
);

CREATE TABLE payment_system_credits
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    credit_limit    DECIMAL(19, 4)   NOT NULL,
    utilized_amount DECIMAL(19, 4) DEFAULT 0,
    statement_day   TINYINT UNSIGNED NOT NULL CHECK (statement_day BETWEEN 1 AND 31),
    due_day         TINYINT UNSIGNED NOT NULL CHECK (due_day BETWEEN 1 AND 31),
    FOREIGN KEY (id) REFERENCES payment_systems (id)
);
