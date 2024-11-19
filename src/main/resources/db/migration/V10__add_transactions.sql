CREATE TABLE transaction_categories
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(100)                           NOT NULL,
    type     ENUM ('INCOME', 'EXPENSE', 'TRANSFER') NOT NULL,
    user_id  INT                                    NOT NULL,
    created  TIMESTAMP                              NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified TIMESTAMP                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    UNIQUE (name, type),
    INDEX (type)
);

CREATE TABLE transactions
(
    id                     BIGINT PRIMARY KEY AUTO_INCREMENT,
    name                   VARCHAR(255)                           NOT NULL,
    description            TEXT,
    type                   ENUM ('INCOME', 'EXPENSE', 'TRANSFER') NOT NULL,
    category_id            INT,
    amount                 DECIMAL(19, 4)                         NOT NULL,
    currency_id            INT                                    NOT NULL,
    date                   DATETIME                               NOT NULL,
    user_id                INT                                    NOT NULL,
    from_asset_id          INT,
    to_asset_id            INT,
    from_payment_system_id INT,
    to_payment_system_id   INT,
    created                TIMESTAMP                              NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified               TIMESTAMP                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES transaction_categories (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (from_asset_id) REFERENCES assets (id),
    FOREIGN KEY (to_asset_id) REFERENCES assets (id),
    FOREIGN KEY (from_payment_system_id) REFERENCES payment_systems (id),
    FOREIGN KEY (to_payment_system_id) REFERENCES payment_systems (id),
    INDEX (type),
    INDEX (date),
    INDEX (created),
    INDEX (modified)
);