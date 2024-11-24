ALTER TABLE assets
    ADD CONSTRAINT unique_type_name_user UNIQUE (type_id, name, user_id);

ALTER TABLE payment_systems
    ADD CONSTRAINT unique_type_name_user UNIQUE (type_id, name, user_id);

ALTER TABLE transactions
    ADD CONSTRAINT unique_type_name_date_user UNIQUE (type, name, date, user_id);

ALTER TABLE transaction_categories
    DROP INDEX name;

ALTER TABLE transaction_categories
    ADD CONSTRAINT unique_type_name_user UNIQUE (type, name, user_id);

ALTER TABLE shared_transactions
    ADD CONSTRAINT unique_transaction_user UNIQUE (transaction_id, user_id);