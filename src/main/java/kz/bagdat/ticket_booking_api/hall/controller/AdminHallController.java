package kz.bagdat.ticket_booking_api.hall.controller;

import jakarta.validation.Valid;
import kz.bagdat.ticket_booking_api.hall.dto.CreateHallRequest;
import kz.bagdat.ticket_booking_api.hall.dto.HallResponse;
import kz.bagdat.ticket_booking_api.hall.dto.UpdateHallRequest;
import kz.bagdat.ticket_booking_api.hall.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/halls")
@RequiredArgsConstructor
public class AdminHallController {

    private final HallService hallService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HallResponse createHall(@Valid @RequestBody CreateHallRequest request) {
        return hallService.createHall(request);
    }

    @PutMapping("/{id}")
    public HallResponse updateHall(
            @PathVariable Long id,
            @Valid @RequestBody UpdateHallRequest request
    ) {
        return hallService.updateHall(id, request);
    }
}