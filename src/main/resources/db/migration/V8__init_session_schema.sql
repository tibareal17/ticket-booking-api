CREATE TABLE sessions
(
    id         BIGSERIAL PRIMARY KEY,
    event_id   BIGINT         NOT NULL,
    hall_id    BIGINT         NOT NULL,
    start_time TIMESTAMP      NOT NULL,
    end_time   TIMESTAMP      NOT NULL,
    price      NUMERIC(10, 2) NOT NULL,
    status     VARCHAR(50)    NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sessions_event
        FOREIGN KEY (event_id) REFERENCES events (id),

    CONSTRAINT fk_sessions_hall
        FOREIGN KEY (hall_id) REFERENCES halls (id),

    CONSTRAINT chk_sessions_time
        CHECK (end_time > start_time),

    CONSTRAINT chk_sessions_price
        CHECK (price >= 0)
);

CREATE INDEX idx_sessions_event_id
    ON sessions (event_id);

CREATE INDEX idx_sessions_hall_id
    ON sessions (hall_id);

CREATE INDEX idx_sessions_start_time
    ON sessions (start_time);

CREATE INDEX idx_sessions_status
    ON sessions (status);