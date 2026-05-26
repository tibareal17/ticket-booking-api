package kz.bagdat.ticket_booking_api.common.exception;

public class InvalidSessionStatusException extends RuntimeException {
    public InvalidSessionStatusException(String message) {
        super(message);
    }
}
