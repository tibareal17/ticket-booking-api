CREATE TABLE halls
(
    id         BIGSERIAL PRIMARY KEY,
    venue_id   BIGINT       NOT NULL,
    name       VARCHAR(100) NOT NULL,
    capacity   INT          NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_halls_venue
        FOREIGN KEY (venue_id) REFERENCES venues (id)
);

CREATE UNIQUE INDEX ux_halls_venue_name
    ON halls (venue_id, lower(name));