package kz.bagdat.ticket_booking_api.session.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SessionResponse(
        Long id,
        Long eventId,
        String eventTitle,
        Long hallId,
        String hallName,
        Long venueId,
        String venueName,
        Long cityId,
        String cityName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        BigDecimal price,
        String status
) {
}