package kz.bagdat.ticket_booking_api.session.service;

import kz.bagdat.ticket_booking_api.common.exception.SessionNotFoundException;
import kz.bagdat.ticket_booking_api.session.dto.CreateSessionRequest;
import kz.bagdat.ticket_booking_api.session.dto.SessionResponse;
import kz.bagdat.ticket_booking_api.session.dto.UpdateSessionRequest;
import kz.bagdat.ticket_booking_api.session.entity.SessionEntity;
import kz.bagdat.ticket_booking_api.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    @Transactional(readOnly = true)
    public List<SessionResponse> getAllSessions(Long eventId) {
        return sessionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SessionResponse getSessionById(Long id) {
        return toResponse(sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException("Session not found")));
    }
//
//    @Transactional
//    public SessionResponse createSession(CreateSessionRequest request) {
//
//    }
//
//    @Transactional
//    public SessionResponse updateSession(Long id, UpdateSessionRequest request) {
//
//    }

    public SessionResponse toResponse(SessionEntity entity) {
        return new SessionResponse(entity.getId(), entity.getEvent().getId(), entity.getEvent().getTitle(), entity.getHall().getId(), entity.getHall().getName(),
                entity.getHall().getVenue().getId(), entity.getHall().getVenue().getName(), entity.getHall().getVenue().getCity().getId(), entity.getHall().getVenue().getCity().getName(),
                entity.getStartTime(), entity.getEndTime(), entity.getPrice(), entity.getStatus().name());
    }
}
