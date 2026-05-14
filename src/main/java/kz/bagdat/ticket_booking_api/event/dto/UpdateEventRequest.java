package kz.bagdat.ticket_booking_api.event.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEventRequest(
        @NotNull Long categoryId,
        @NotBlank String title,
        String description,
        @Min(1) Integer durationMinutes,
        String ageRating,
        String posterUrl,
        @NotBlank String status
) {
}