package kz.bagdat.ticket_booking_api.event.controller;

import kz.bagdat.ticket_booking_api.event.dto.EventResponse;
import kz.bagdat.ticket_booking_api.event.service.EventService;
import kz.bagdat.ticket_booking_api.session.dto.SessionResponse;
import kz.bagdat.ticket_booking_api.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final SessionService sessionService;

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

    @GetMapping("/{eventId}/sessions")
    public List<SessionResponse> getEventSessions(@PathVariable Long eventId) {
        return sessionService.getAllSessions(eventId);
    }
}
