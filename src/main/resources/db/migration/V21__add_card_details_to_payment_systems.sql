ALTER TABLE payment_systems
    ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE,
    ADD COLUMN card_holder_name VARCHAR(255) NULL,
    ADD COLUMN card_number VARCHAR(255) NULL,
    ADD COLUMN security_code VARCHAR(255) NULL,
    ADD COLUMN expiry_date VARCHAR(100) NULL,
    ADD COLUMN additional_note TEXT NULL;