CREATE TABLE shared_transactions
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id BIGINT         NOT NULL,
    user_id        INT            NOT NULL,
    share          DECIMAL(19, 4) NOT NULL,
    paid_amount    DECIMAL(19, 4) NOT NULL,
    is_settled     BOOLEAN                 DEFAULT FALSE,
    created        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (transaction_id) REFERENCES transactions (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);