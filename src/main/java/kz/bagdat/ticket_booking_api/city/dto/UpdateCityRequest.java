package kz.bagdat.ticket_booking_api.city.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCityRequest(
        @NotBlank String name,
        @NotBlank String country
) {
}