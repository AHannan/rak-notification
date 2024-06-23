CREATE TABLE notification (
    id                      VARCHAR(255) PRIMARY KEY,
    user_id                 VARCHAR(255) NOT NULL,
    message                 TEXT NOT NULL,
    read                    BOOLEAN NOT NULL DEFAULT FALSE,
    creation_timestamp      TIMESTAMP NOT NULL,
    modification_timestamp  TIMESTAMP NOT NULL
);