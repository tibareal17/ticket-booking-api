package kz.bagdat.ticket_booking_api.city.controller;

import jakarta.validation.Valid;
import kz.bagdat.ticket_booking_api.city.dto.CityResponse;
import kz.bagdat.ticket_booking_api.city.dto.CreateCityRequest;
import kz.bagdat.ticket_booking_api.city.dto.UpdateCityRequest;
import kz.bagdat.ticket_booking_api.city.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/cities")
@RequiredArgsConstructor
public class AdminCityController {

    private final CityService cityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponse createCity(@Valid @RequestBody CreateCityRequest request) {
        return cityService.createCity(request);
    }

    @PutMapping("/{id}")
    public CityResponse updateCity(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCityRequest request
    ) {
        return cityService.updateCity(id, request);
    }
}