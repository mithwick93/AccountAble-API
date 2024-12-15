CREATE TABLE installment_plans
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(100)                        NOT NULL,
    description        TEXT,
    liability_id       INT                                 NOT NULL,
    currency_id        INT                                 NOT NULL,
    user_id            INT                                 NOT NULL,
    installment_amount DECIMAL(19, 4)                      NOT NULL,
    total_installments INT                                 NOT NULL,
    installments_paid  INT       DEFAULT 0,
    interest_rate      DECIMAL(5, 2),
    start_date         DATE                                NOT NULL,
    end_date           DATE,
    status             ENUM (
        'ACTIVE',
        'SETTLED',
        'DEFAULTED',
        'CANCELED',
        'DEFERRED',
        'OVERDUE',
        'RESTRUCTURED'
        )                        DEFAULT 'ACTIVE',
    created            TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified           TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,


    FOREIGN KEY (liability_id) REFERENCES liabilities (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
