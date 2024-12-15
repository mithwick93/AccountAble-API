CREATE TABLE liability_types
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE liabilities
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)                        NOT NULL,
    description   TEXT,
    type_id       INT                                 NOT NULL,
    currency_id   INT                                 NOT NULL,
    user_id       INT                                 NOT NULL,
    amount        DECIMAL(19, 4)                      NOT NULL,
    balance       DECIMAL(19, 4)                      NOT NULL,
    interest_rate DECIMAL(5, 2),
    statement_day TINYINT CHECK (statement_day BETWEEN 1 AND 31),
    due_day       TINYINT                             NOT NULL CHECK (due_day BETWEEN 1 AND 31),
    status        ENUM (
        'ACTIVE',
        'SETTLED',
        'DEFAULTED',
        'CLOSED',
        'IN_DISPUTE',
        'SUSPENDED',
        'PENDING_ACTIVATION'
        )                   DEFAULT 'ACTIVE',
    created       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (type_id) REFERENCES liability_types (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


