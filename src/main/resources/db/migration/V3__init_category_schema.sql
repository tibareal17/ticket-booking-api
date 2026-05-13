CREATE TABLE categories
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    slug       VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO categories (name, slug)
VALUES ('Movie', 'movie'),
       ('Theatre', 'theatre'),
       ('Sport', 'sport'),
       ('Standup', 'standup'),
       ('Concert', 'concert'),
       ('Show', 'show'),
       ('Tour', 'tour');