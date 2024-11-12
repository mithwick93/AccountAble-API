CREATE TABLE asset_types
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL
);

CREATE TABLE currencies
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL,
    code        CHAR(3)      NOT NULL
);

CREATE TABLE assets
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    type_id     INT            NOT NULL,
    name        VARCHAR(100)   NOT NULL,
    description TEXT           NOT NULL,
    balance     DECIMAL(19, 4) NOT NULL,
    currency_id INT            NOT NULL,
    user_id     INT            NOT NULL,

    FOREIGN KEY (type_id) REFERENCES asset_types (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);