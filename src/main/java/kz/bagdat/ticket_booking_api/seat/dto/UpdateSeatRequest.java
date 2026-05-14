package kz.bagdat.ticket_booking_api.seat.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSeatRequest(
        @NotNull Long hallId,
        @NotNull @Min(1) Integer rowNumber,
        @NotNull @Min(1) Integer seatNumber,
        @NotBlank String seatType
) {
}