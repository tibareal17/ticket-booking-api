package kz.bagdat.ticket_booking_api.session.controller;

import kz.bagdat.ticket_booking_api.session.dto.SessionResponse;
import kz.bagdat.ticket_booking_api.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public List<SessionResponse> getAllSessions(
            @RequestParam(required = false) Long eventId
    ) {
        return sessionService.getAllSessions(eventId);
    }

    @GetMapping("/{id}")
    public SessionResponse getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id);
    }
}
