package kz.bagdat.ticket_booking_api.seat.controller;

import kz.bagdat.ticket_booking_api.seat.dto.SeatResponse;
import kz.bagdat.ticket_booking_api.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public List<SeatResponse> getAllSeats(
            @RequestParam(required = false) Long hallId
    ) {
        return seatService.getAllSeats(hallId);
    }

    @GetMapping("/{id}")
    public SeatResponse getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }
}