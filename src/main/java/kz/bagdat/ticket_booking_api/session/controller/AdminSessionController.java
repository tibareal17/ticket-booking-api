package kz.bagdat.ticket_booking_api.session.controller;

import jakarta.validation.Valid;
import kz.bagdat.ticket_booking_api.session.dto.CreateSessionRequest;
import kz.bagdat.ticket_booking_api.session.dto.SessionResponse;
import kz.bagdat.ticket_booking_api.session.dto.UpdateSessionRequest;
import kz.bagdat.ticket_booking_api.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/sessions")
@RequiredArgsConstructor
public class AdminSessionController {

    private final SessionService sessionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse createSession(@Valid @RequestBody CreateSessionRequest request) {
        return sessionService.createSession(request);
    }

    @PutMapping("/{id}")
    public SessionResponse updateSession(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSessionRequest request
    ) {
        return sessionService.updateSession(id, request);
    }
}
