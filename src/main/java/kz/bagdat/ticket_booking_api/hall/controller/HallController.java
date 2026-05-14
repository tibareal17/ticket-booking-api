package kz.bagdat.ticket_booking_api.hall.controller;

import kz.bagdat.ticket_booking_api.hall.dto.HallResponse;
import kz.bagdat.ticket_booking_api.hall.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @GetMapping
    public List<HallResponse> getAllHalls(
            @RequestParam(required = false) Long venueId
    ) {
        return hallService.getAllHalls(venueId);
    }

    @GetMapping("/{id}")
    public HallResponse getHallById(@PathVariable Long id) {
        return hallService.getHallById(id);
    }
}