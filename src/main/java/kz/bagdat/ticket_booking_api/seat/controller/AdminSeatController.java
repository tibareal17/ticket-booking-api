package kz.bagdat.ticket_booking_api.seat.controller;

import jakarta.validation.Valid;
import kz.bagdat.ticket_booking_api.seat.dto.CreateSeatRequest;
import kz.bagdat.ticket_booking_api.seat.dto.SeatResponse;
import kz.bagdat.ticket_booking_api.seat.dto.UpdateSeatRequest;
import kz.bagdat.ticket_booking_api.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/seats")
@RequiredArgsConstructor
public class AdminSeatController {

    private final SeatService seatService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeatResponse createSeat(@Valid @RequestBody CreateSeatRequest request) {
        return seatService.createSeat(request);
    }

    @PutMapping("/{id}")
    public SeatResponse updateSeat(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSeatRequest request
    ) {
        return seatService.updateSeat(id, request);
    }
}