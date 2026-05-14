package kz.bagdat.ticket_booking_api.hall.dto;

public record HallResponse(
        Long id,
        Long venueId,
        String venueName,
        String name,
        Integer capacity
) {
}