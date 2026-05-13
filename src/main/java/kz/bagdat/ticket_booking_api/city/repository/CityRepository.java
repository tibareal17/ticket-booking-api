package kz.bagdat.ticket_booking_api.city.repository;

import kz.bagdat.ticket_booking_api.city.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<CityEntity> findByNameIgnoreCase(String name);
}
