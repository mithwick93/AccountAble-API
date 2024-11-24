DROP INDEX date ON transactions;

ALTER TABLE transactions
    MODIFY COLUMN date DATE NOT NULL;

create index date
    on transactions (date);