package kz.bagdat.ticket_booking_api.session.service;

import kz.bagdat.ticket_booking_api.common.exception.EventNotFoundException;
import kz.bagdat.ticket_booking_api.common.exception.HallNotFoundException;
import kz.bagdat.ticket_booking_api.common.exception.InvalidSessionStatusException;
import kz.bagdat.ticket_booking_api.common.exception.InvalidSessionTimeException;
import kz.bagdat.ticket_booking_api.common.exception.SessionNotFoundException;
import kz.bagdat.ticket_booking_api.event.entity.EventEntity;
import kz.bagdat.ticket_booking_api.event.repository.EventRepository;
import kz.bagdat.ticket_booking_api.hall.entity.HallEntity;
import kz.bagdat.ticket_booking_api.hall.repository.HallRepository;
import kz.bagdat.ticket_booking_api.session.dto.CreateSessionRequest;
import kz.bagdat.ticket_booking_api.session.dto.SessionResponse;
import kz.bagdat.ticket_booking_api.session.dto.UpdateSessionRequest;
import kz.bagdat.ticket_booking_api.session.entity.SessionEntity;
import kz.bagdat.ticket_booking_api.session.entity.SessionStatus;
import kz.bagdat.ticket_booking_api.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final EventRepository eventRepository;
    private final HallRepository hallRepository;

    @Transactional(readOnly = true)
    public List<SessionResponse> getAllSessions(Long eventId) {
        List<SessionEntity> sessions = eventId == null
                ? sessionRepository.findAll()
                : getSessionsByEventId(eventId);

        return sessions
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SessionResponse getSessionById(Long id) {
        SessionEntity session = sessionRepository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));

        return toResponse(session);
    }

    @Transactional
    public SessionResponse createSession(CreateSessionRequest request) {
        validateSessionTime(request.startTime(), request.endTime());

        EventEntity event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        HallEntity hall = hallRepository.findById(request.hallId())
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));

        SessionEntity session = new SessionEntity();
        session.setEvent(event);
        session.setHall(hall);
        session.setStartTime(request.startTime());
        session.setEndTime(request.endTime());
        session.setPrice(request.price());
        session.setStatus(SessionStatus.SCHEDULED);
        session.setCreatedAt(LocalDateTime.now());

        SessionEntity savedSession = sessionRepository.save(session);

        return toResponse(savedSession);
    }

    @Transactional
    public SessionResponse updateSession(Long id, UpdateSessionRequest request) {
        SessionEntity session = sessionRepository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));

        validateSessionTime(request.startTime(), request.endTime());

        EventEntity event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        HallEntity hall = hallRepository.findById(request.hallId())
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));

        SessionStatus status = parseStatus(request.status());

        session.setEvent(event);
        session.setHall(hall);
        session.setStartTime(request.startTime());
        session.setEndTime(request.endTime());
        session.setPrice(request.price());
        session.setStatus(status);

        SessionEntity savedSession = sessionRepository.save(session);

        return toResponse(savedSession);
    }

    private void validateSessionTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (!endTime.isAfter(startTime)) {
            throw new InvalidSessionTimeException("Session end time must be after start time");
        }
    }

    private List<SessionEntity> getSessionsByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Event not found");
        }

        return sessionRepository.findAllByEvent_Id(eventId);
    }

    private SessionStatus parseStatus(String status) {
        try {
            return SessionStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidSessionStatusException("Invalid session status");
        }
    }

    private SessionResponse toResponse(SessionEntity entity) {
        return new SessionResponse(
                entity.getId(),
                entity.getEvent().getId(),
                entity.getEvent().getTitle(),
                entity.getHall().getId(),
                entity.getHall().getName(),
                entity.getHall().getVenue().getId(),
                entity.getHall().getVenue().getName(),
                entity.getHall().getVenue().getCity().getId(),
                entity.getHall().getVenue().getCity().getName(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getPrice(),
                entity.getStatus().name()
        );
    }
}
