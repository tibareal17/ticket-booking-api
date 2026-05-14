CREATE TABLE venues
(
    id          BIGSERIAL PRIMARY KEY,
    city_id     BIGINT       NOT NULL,
    name        VARCHAR(150) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_venues_city
        FOREIGN KEY (city_id) REFERENCES cities (id)
);

CREATE UNIQUE INDEX ux_venues_city_name
    ON venues (city_id, lower(name));