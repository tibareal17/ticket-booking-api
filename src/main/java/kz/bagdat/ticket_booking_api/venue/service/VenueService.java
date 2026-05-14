package kz.bagdat.ticket_booking_api.venue.service;

import kz.bagdat.ticket_booking_api.city.entity.CityEntity;
import kz.bagdat.ticket_booking_api.city.repository.CityRepository;
import kz.bagdat.ticket_booking_api.common.exception.CityNotFoundException;
import kz.bagdat.ticket_booking_api.common.exception.VenueAlreadyExistsException;
import kz.bagdat.ticket_booking_api.common.exception.VenueNotFoundException;
import kz.bagdat.ticket_booking_api.venue.dto.CreateVenueRequest;
import kz.bagdat.ticket_booking_api.venue.dto.UpdateVenueRequest;
import kz.bagdat.ticket_booking_api.venue.dto.VenueResponse;
import kz.bagdat.ticket_booking_api.venue.entity.VenueEntity;
import kz.bagdat.ticket_booking_api.venue.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<VenueResponse> getAllVenues(Long cityId) {
        List<VenueEntity> venues = cityId == null
                ? venueRepository.findAll()
                : venueRepository.findAllByCity_Id(cityId);

        return venues.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public VenueResponse getVenueById(Long id) {
        VenueEntity venue = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException("Venue not found"));

        return toResponse(venue);
    }

    @Transactional
    public VenueResponse createVenue(CreateVenueRequest request) {
        Long cityId = request.cityId();
        String name = request.name().trim();
        String address = request.address().trim();
        String description = request.description();

        CityEntity city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        if (venueRepository.existsByCity_IdAndNameIgnoreCase(cityId, name)) {
            throw new VenueAlreadyExistsException("Venue with this name already exists in this city");
        }

        VenueEntity venue = new VenueEntity();
        venue.setCity(city);
        venue.setName(name);
        venue.setAddress(address);
        venue.setDescription(description);
        venue.setCreatedAt(LocalDateTime.now());

        VenueEntity savedVenue = venueRepository.save(venue);

        return toResponse(savedVenue);
    }

    @Transactional
    public VenueResponse updateVenue(Long id, UpdateVenueRequest request) {
        VenueEntity venue = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException("Venue not found"));

        Long newCityId = request.cityId();
        String newName = request.name().trim();
        String newAddress = request.address().trim();
        String newDescription = request.description();

        CityEntity city = cityRepository.findById(newCityId)
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        venueRepository.findByCity_IdAndNameIgnoreCase(newCityId, newName)
                .filter(existingVenue -> !existingVenue.getId().equals(id))
                .ifPresent(existingVenue -> {
                    throw new VenueAlreadyExistsException("Venue with this name already exists in this city");
                });

        venue.setCity(city);
        venue.setName(newName);
        venue.setAddress(newAddress);
        venue.setDescription(newDescription);

        VenueEntity savedVenue = venueRepository.save(venue);

        return toResponse(savedVenue);
    }

    private VenueResponse toResponse(VenueEntity venue) {
        return new VenueResponse(
                venue.getId(),
                venue.getCity().getId(),
                venue.getCity().getName(),
                venue.getName(),
                venue.getAddress(),
                venue.getDescription()
        );
    }
}