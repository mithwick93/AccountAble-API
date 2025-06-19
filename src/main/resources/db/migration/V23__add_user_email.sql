ALTER TABLE users
    ADD COLUMN email VARCHAR(100) NOT NULL DEFAULT 'dummy@example.com' AFTER user_name;