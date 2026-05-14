package kz.bagdat.ticket_booking_api.hall.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateHallRequest(
        @NotNull Long venueId,
        @NotBlank String name,
        @NotNull @Min(1) Integer capacity
) {
}