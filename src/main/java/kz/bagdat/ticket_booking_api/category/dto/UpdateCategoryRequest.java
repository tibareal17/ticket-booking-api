package kz.bagdat.ticket_booking_api.category.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequest(
        @NotBlank String name,
        @NotBlank String slug
) {
}