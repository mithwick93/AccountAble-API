ALTER TABLE payment_system_credits
    DROP COLUMN credit_limit,
    DROP COLUMN utilized_amount,
    DROP COLUMN statement_day,
    DROP COLUMN due_day;

ALTER TABLE payment_system_credits
    ADD COLUMN liability_id INT NOT NULL,
    ADD CONSTRAINT payment_system_credits_liability_fk
        FOREIGN KEY (liability_id) REFERENCES liabilities (id);