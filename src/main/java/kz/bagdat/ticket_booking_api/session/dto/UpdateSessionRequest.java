package kz.bagdat.ticket_booking_api.session.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateSessionRequest(
        @NotNull Long eventId,
        @NotNull Long hallId,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotBlank String status
) {
}