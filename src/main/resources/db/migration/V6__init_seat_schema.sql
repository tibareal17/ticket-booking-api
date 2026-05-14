CREATE TABLE seats
(
    id          BIGSERIAL PRIMARY KEY,
    hall_id     BIGINT      NOT NULL,
    row_number  INT         NOT NULL,
    seat_number INT         NOT NULL,
    seat_type   VARCHAR(50) NOT NULL DEFAULT 'STANDARD',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_seats_hall
        FOREIGN KEY (hall_id) REFERENCES halls (id)
);

CREATE UNIQUE INDEX ux_seats_hall_row_seat
    ON seats (hall_id, row_number, seat_number);