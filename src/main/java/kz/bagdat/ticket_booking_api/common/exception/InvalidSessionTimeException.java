package kz.bagdat.ticket_booking_api.common.exception;

public class InvalidSessionTimeException extends RuntimeException {
    public InvalidSessionTimeException(String message) {
        super(message);
    }
}
