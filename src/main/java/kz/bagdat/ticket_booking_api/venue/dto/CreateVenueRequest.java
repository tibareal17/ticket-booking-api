package kz.bagdat.ticket_booking_api.venue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVenueRequest(
        @NotNull Long cityId,
        @NotBlank String name,
        @NotBlank String address,
        String description
) {
}