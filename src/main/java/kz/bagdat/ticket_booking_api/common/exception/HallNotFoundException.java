package kz.bagdat.ticket_booking_api.common.exception;

public class HallNotFoundException extends RuntimeException {
    public HallNotFoundException(String message) {
        super(message);
    }
}
