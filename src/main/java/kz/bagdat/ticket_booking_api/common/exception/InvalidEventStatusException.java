package kz.bagdat.ticket_booking_api.common.exception;

public class InvalidEventStatusException extends RuntimeException {
    public InvalidEventStatusException(String message) {
        super(message);
    }
}
