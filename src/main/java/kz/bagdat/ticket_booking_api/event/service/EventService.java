package kz.bagdat.ticket_booking_api.event.service;

import kz.bagdat.ticket_booking_api.category.entity.CategoryEntity;
import kz.bagdat.ticket_booking_api.category.repository.CategoryRepository;
import kz.bagdat.ticket_booking_api.common.exception.CategoryNotFoundException;
import kz.bagdat.ticket_booking_api.common.exception.EventAlreadyExistsException;
import kz.bagdat.ticket_booking_api.common.exception.EventNotFoundException;
import kz.bagdat.ticket_booking_api.common.exception.InvalidEventStatusException;
import kz.bagdat.ticket_booking_api.event.dto.CreateEventRequest;
import kz.bagdat.ticket_booking_api.event.dto.EventResponse;
import kz.bagdat.ticket_booking_api.event.dto.UpdateEventRequest;
import kz.bagdat.ticket_booking_api.event.entity.EventEntity;
import kz.bagdat.ticket_booking_api.event.entity.EventStatus;
import kz.bagdat.ticket_booking_api.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents(Long categoryId) {
        List<EventEntity> events = categoryId == null
                ? eventRepository.findAll()
                : eventRepository.findAllByCategory_Id(categoryId);

        return events.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EventResponse getEventById(Long id) {
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        return toResponse(event);
    }

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {
        Long categoryId = request.categoryId();
        String title = request.title().trim();

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (eventRepository.existsByTitleIgnoreCase(title)) {
            throw new EventAlreadyExistsException("Event with this title already exists");
        }

        EventEntity event = new EventEntity();
        event.setCategory(category);
        event.setTitle(title);
        event.setDescription(request.description());
        event.setDurationMinutes(request.durationMinutes());
        event.setAgeRating(request.ageRating());
        event.setPosterUrl(request.posterUrl());
        event.setStatus(EventStatus.ACTIVE);
        event.setCreatedAt(LocalDateTime.now());

        EventEntity savedEvent = eventRepository.save(event);

        return toResponse(savedEvent);
    }

    @Transactional
    public EventResponse updateEvent(Long id, UpdateEventRequest request) {
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        Long categoryId = request.categoryId();
        String newTitle = request.title().trim();

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        // Пока проверка простая. Позже можно заменить на findByTitleIgnoreCase и проверять id.
        if (!event.getTitle().equalsIgnoreCase(newTitle)
                && eventRepository.existsByTitleIgnoreCase(newTitle)) {
            throw new EventAlreadyExistsException("Event with this title already exists");
        }

        EventStatus newStatus = parseStatus(request.status());

        event.setCategory(category);
        event.setTitle(newTitle);
        event.setDescription(request.description());
        event.setDurationMinutes(request.durationMinutes());
        event.setAgeRating(request.ageRating());
        event.setPosterUrl(request.posterUrl());
        event.setStatus(newStatus);

        EventEntity savedEvent = eventRepository.save(event);

        return toResponse(savedEvent);
    }

    private EventResponse toResponse(EventEntity event) {
        return new EventResponse(
                event.getId(),
                event.getCategory().getId(),
                event.getCategory().getName(),
                event.getTitle(),
                event.getDescription(),
                event.getDurationMinutes(),
                event.getAgeRating(),
                event.getPosterUrl(),
                event.getStatus().name()
        );
    }

    private EventStatus parseStatus(String status) {
        try {
            return EventStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidEventStatusException("Invalid event status");
        }
    }
}
