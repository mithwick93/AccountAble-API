ALTER TABLE assets
    MODIFY COLUMN balance DECIMAL(19, 4) NOT NULL CHECK (balance >= 0);

ALTER TABLE payment_system_debits
    MODIFY COLUMN daily_limit DECIMAL(19, 4) NULL CHECK (daily_limit >= 0);

ALTER TABLE payment_system_credits
    MODIFY COLUMN credit_limit DECIMAL(19, 4) NOT NULL CHECK (credit_limit >= 0),
    MODIFY COLUMN utilized_amount DECIMAL(19, 4) DEFAULT 0 CHECK (utilized_amount >= 0);
