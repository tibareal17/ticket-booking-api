CREATE TABLE events
(
    id               BIGSERIAL PRIMARY KEY,
    category_id      BIGINT       NOT NULL,
    title            VARCHAR(200) NOT NULL,
    description      TEXT,
    duration_minutes INT,
    age_rating       VARCHAR(20),
    poster_url       VARCHAR(500),
    status           VARCHAR(50)  NOT NULL DEFAULT 'ACTIVE',
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_events_category
        FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE INDEX idx_events_category_id
    ON events (category_id);

CREATE INDEX idx_events_status
    ON events (status);