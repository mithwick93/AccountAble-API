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

INSERT INTO asset_types (id, name, description)
VALUES (1, 'Savings Account', 'A traditional savings account for storing money'),
       (2, 'Investment', 'An investment account for growing wealth');

INSERT INTO currencies (id, name, description, code)
VALUES (1, 'Sri Lankan Rupee', 'The official currency of Sri Lanka', 'LKR'),
       (2, 'Swedish Krona', 'The official currency of Sweden', 'SEK');