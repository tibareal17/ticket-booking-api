package kz.bagdat.ticket_booking_api.common.exception;

public class VenueAlreadyExistsException extends RuntimeException {
    public VenueAlreadyExistsException(String message) {
        super(message);
    }
}
