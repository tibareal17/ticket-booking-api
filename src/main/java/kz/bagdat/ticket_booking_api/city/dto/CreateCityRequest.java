package kz.bagdat.ticket_booking_api.city.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCityRequest(
        @NotBlank String name,
        @NotBlank String country
) {
}