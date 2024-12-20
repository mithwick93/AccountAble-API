ALTER TABLE transactions
    ADD COLUMN from_liability_id INT NULL,
    ADD COLUMN to_liability_id   INT NULL;

ALTER TABLE transactions
    ADD CONSTRAINT transactions_ibfk_8 FOREIGN KEY (from_liability_id) REFERENCES liabilities (id),
    ADD CONSTRAINT transactions_ibfk_9 FOREIGN KEY (to_liability_id) REFERENCES liabilities (id);

CREATE INDEX from_liability_id ON transactions (from_liability_id);
CREATE INDEX to_liability_id ON transactions (to_liability_id);