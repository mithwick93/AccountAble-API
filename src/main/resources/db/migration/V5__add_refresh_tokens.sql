CREATE TABLE refresh_tokens
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT          NOT NULL,
    token       VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);