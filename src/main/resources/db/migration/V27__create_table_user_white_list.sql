CREATE TABLE user_whitelist
(
    email VARCHAR(100) PRIMARY KEY,
    CONSTRAINT fk_user_email
        FOREIGN KEY (email)
            REFERENCES users (email)
            ON DELETE CASCADE
);