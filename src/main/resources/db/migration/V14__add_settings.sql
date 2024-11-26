CREATE TABLE settings
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT          NOT NULL,
    setting_key   VARCHAR(255) NOT NULL,
    setting_value JSON         NOT NULL,
    created       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    UNIQUE (user_id, setting_key)
);