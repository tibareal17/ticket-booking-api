package kz.bagdat.ticket_booking_api.auth.dto;

public record AuthResponse(String accessToken,
                           String tokenType) {
}
