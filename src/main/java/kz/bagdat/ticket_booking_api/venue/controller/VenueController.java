package kz.bagdat.ticket_booking_api.venue.controller;

import kz.bagdat.ticket_booking_api.venue.dto.VenueResponse;
import kz.bagdat.ticket_booking_api.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @GetMapping
    public List<VenueResponse> getAllVenues(
            @RequestParam(required = false) Long cityId
    ) {
        return venueService.getAllVenues(cityId);
    }

    @GetMapping("/{id}")
    public VenueResponse getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id);
    }
}