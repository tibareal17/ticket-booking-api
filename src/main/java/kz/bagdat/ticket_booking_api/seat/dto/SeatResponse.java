package kz.bagdat.ticket_booking_api.seat.dto;

public record SeatResponse(
        Long id,
        Long hallId,
        String hallName,
        Integer rowNumber,
        Integer seatNumber,
        String seatType
) {
}