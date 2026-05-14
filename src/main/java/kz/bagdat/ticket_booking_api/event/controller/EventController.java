package kz.bagdat.ticket_booking_api.event.controller;

import kz.bagdat.ticket_booking_api.event.dto.EventResponse;
import kz.bagdat.ticket_booking_api.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventResponse> getAllEvents(
            @RequestParam(required = false) Long categoryId
    ) {
        return eventService.getAllEvents(categoryId);
    }

    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }
}