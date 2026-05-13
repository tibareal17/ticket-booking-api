package kz.bagdat.ticket_booking_api.category.dto;

public record CategoryResponse(
        Long id,
        String name,
        String slug
) {
}