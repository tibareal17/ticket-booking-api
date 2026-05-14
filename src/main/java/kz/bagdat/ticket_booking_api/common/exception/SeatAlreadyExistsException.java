package kz.bagdat.ticket_booking_api.common.exception;

public class SeatAlreadyExistsException extends RuntimeException {
    public SeatAlreadyExistsException(String message) {
        super(message);
    }
}
