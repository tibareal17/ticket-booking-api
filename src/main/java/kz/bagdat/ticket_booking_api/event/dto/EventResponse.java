package kz.bagdat.ticket_booking_api.event.dto;

public record EventResponse(
        Long id,
        Long categoryId,
        String categoryName,
        String title,
        String description,
        Integer durationMinutes,
        String ageRating,
        String posterUrl,
        String status
) {
}