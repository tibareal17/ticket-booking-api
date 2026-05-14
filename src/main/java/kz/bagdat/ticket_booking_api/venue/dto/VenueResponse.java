package kz.bagdat.ticket_booking_api.venue.dto;

public record VenueResponse(
        Long id,
        Long cityId,
        String cityName,
        String name,
        String address,
        String description
) {
}