package kz.bagdat.ticket_booking_api.event.controller;

import jakarta.validation.Valid;
import kz.bagdat.ticket_booking_api.event.dto.CreateEventRequest;
import kz.bagdat.ticket_booking_api.event.dto.EventResponse;
import kz.bagdat.ticket_booking_api.event.dto.UpdateEventRequest;
import kz.bagdat.ticket_booking_api.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@Valid @RequestBody CreateEventRequest request) {
        return eventService.createEvent(request);
    }

    @PutMapping("/{id}")
    public EventResponse updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventRequest request
    ) {
        return eventService.updateEvent(id, request);
    }
}