package kz.bagdat.ticket_booking_api.venue.controller;

import jakarta.validation.Valid;
import kz.bagdat.ticket_booking_api.venue.dto.CreateVenueRequest;
import kz.bagdat.ticket_booking_api.venue.dto.UpdateVenueRequest;
import kz.bagdat.ticket_booking_api.venue.dto.VenueResponse;
import kz.bagdat.ticket_booking_api.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/venues")
@RequiredArgsConstructor
public class AdminVenueController {

    private final VenueService venueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueResponse createVenue(@Valid @RequestBody CreateVenueRequest request) {
        return venueService.createVenue(request);
    }

    @PutMapping("/{id}")
    public VenueResponse updateVenue(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVenueRequest request
    ) {
        return venueService.updateVenue(id, request);
    }
}