package kz.bagdat.ticket_booking_api.common.exception;

public class HallAlreadyExistsException extends RuntimeException {
    public HallAlreadyExistsException(String message) {
        super(message);
    }
}
