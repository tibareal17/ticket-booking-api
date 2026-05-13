CREATE TABLE cities
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    country    VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO cities (name, country)
VALUES ('Astana', 'Kazakhstan'),
       ('Almaty', 'Kazakhstan'),
       ('Shymkent', 'Kazakhstan');