package kz.bagdat.ticket_booking_api.city.service;

import kz.bagdat.ticket_booking_api.city.dto.CityResponse;
import kz.bagdat.ticket_booking_api.city.dto.CreateCityRequest;
import kz.bagdat.ticket_booking_api.city.dto.UpdateCityRequest;
import kz.bagdat.ticket_booking_api.city.entity.CityEntity;
import kz.bagdat.ticket_booking_api.city.repository.CityRepository;
import kz.bagdat.ticket_booking_api.common.exception.CityAlreadyExistsException;
import kz.bagdat.ticket_booking_api.common.exception.CityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<CityResponse> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CityResponse getCityById(Long id) {
        CityEntity city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        return toResponse(city);
    }

    @Transactional
    public CityResponse createCity(CreateCityRequest request) {
        String name = request.name().trim();
        String country = request.country().trim();

        if (cityRepository.existsByNameIgnoreCase(name)) {
            throw new CityAlreadyExistsException("City already exists");
        }

        CityEntity city = new CityEntity();
        city.setName(name);
        city.setCountry(country);
        city.setCreatedAt(LocalDateTime.now());

        CityEntity savedCity = cityRepository.save(city);

        return toResponse(savedCity);
    }

    @Transactional
    public CityResponse updateCity(Long id, UpdateCityRequest request) {
        CityEntity city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        String newName = request.name().trim();
        String newCountry = request.country().trim();

        cityRepository.findByNameIgnoreCase(newName)
                .filter(existingCity -> !existingCity.getId().equals(id))
                .ifPresent(existingCity -> {
                    throw new CityAlreadyExistsException("City already exists");
                });

        city.setName(newName);
        city.setCountry(newCountry);

        CityEntity savedCity = cityRepository.save(city);

        return toResponse(savedCity);
    }

    private CityResponse toResponse(CityEntity city) {
        return new CityResponse(
                city.getId(),
                city.getName(),
                city.getCountry()
        );
    }
}