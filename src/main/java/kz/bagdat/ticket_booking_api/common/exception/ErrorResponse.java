package kz.bagdat.ticket_booking_api.common.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String code,
                            String message,
                            LocalDateTime timestamp) {
}
