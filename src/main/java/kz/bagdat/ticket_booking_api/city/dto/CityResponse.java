package kz.bagdat.ticket_booking_api.city.dto;

public record CityResponse(
        Long id,
        String name,
        String country
) {
}