package kz.bagdat.ticket_booking_api.hall.service;

import kz.bagdat.ticket_booking_api.common.exception.HallAlreadyExistsException;
import kz.bagdat.ticket_booking_api.common.exception.HallNotFoundException;
import kz.bagdat.ticket_booking_api.common.exception.VenueNotFoundException;
import kz.bagdat.ticket_booking_api.hall.dto.CreateHallRequest;
import kz.bagdat.ticket_booking_api.hall.dto.HallResponse;
import kz.bagdat.ticket_booking_api.hall.dto.UpdateHallRequest;
import kz.bagdat.ticket_booking_api.hall.entity.HallEntity;
import kz.bagdat.ticket_booking_api.hall.repository.HallRepository;
import kz.bagdat.ticket_booking_api.venue.entity.VenueEntity;
import kz.bagdat.ticket_booking_api.venue.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallService {

    private final HallRepository hallRepository;
    private final VenueRepository venueRepository;

    @Transactional(readOnly = true)
    public List<HallResponse> getAllHalls(Long venueId) {
        List<HallEntity> halls = venueId == null
                ? hallRepository.findAll()
                : hallRepository.findAllByVenue_Id(venueId);

        return halls.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public HallResponse getHallById(Long id) {
        HallEntity hall = hallRepository.findById(id)
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));

        return toResponse(hall);
    }

    @Transactional
    public HallResponse createHall(CreateHallRequest request) {
        Long venueId = request.venueId();
        String name = request.name().trim();

        VenueEntity venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new VenueNotFoundException("Venue not found"));

        if (hallRepository.existsByVenue_IdAndNameIgnoreCase(venueId, name)) {
            throw new HallAlreadyExistsException("Hall with this name already exists in this venue");
        }

        HallEntity hall = new HallEntity();
        hall.setVenue(venue);
        hall.setName(name);
        hall.setCapacity(request.capacity());
        hall.setCreatedAt(LocalDateTime.now());

        HallEntity savedHall = hallRepository.save(hall);

        return toResponse(savedHall);
    }

    @Transactional
    public HallResponse updateHall(Long id, UpdateHallRequest request) {
        HallEntity hall = hallRepository.findById(id)
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));

        Long newVenueId = request.venueId();
        String newName = request.name().trim();

        VenueEntity venue = venueRepository.findById(newVenueId)
                .orElseThrow(() -> new VenueNotFoundException("Venue not found"));

        hallRepository.findByVenue_IdAndNameIgnoreCase(newVenueId, newName)
                .filter(existingHall -> !existingHall.getId().equals(id))
                .ifPresent(existingHall -> {
                    throw new HallAlreadyExistsException("Hall with this name already exists in this venue");
                });

        hall.setVenue(venue);
        hall.setName(newName);
        hall.setCapacity(request.capacity());

        HallEntity savedHall = hallRepository.save(hall);

        return toResponse(savedHall);
    }

    private HallResponse toResponse(HallEntity hall) {
        return new HallResponse(
                hall.getId(),
                hall.getVenue().getId(),
                hall.getVenue().getName(),
                hall.getName(),
                hall.getCapacity()
        );
    }
}