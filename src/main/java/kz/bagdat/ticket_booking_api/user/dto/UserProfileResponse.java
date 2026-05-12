package kz.bagdat.ticket_booking_api.user.dto;

public record UserProfileResponse(
        Long id,
        String email,
        String firstName,
        String lastName
) {
}