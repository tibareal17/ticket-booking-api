package kz.bagdat.ticket_booking_api.venue.repository;

import kz.bagdat.ticket_booking_api.venue.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends JpaRepository<VenueEntity, Long> {

    List<VenueEntity> findAllByCity_Id(Long cityId);

    Optional<VenueEntity> findByCity_IdAndNameIgnoreCase(Long cityId, String name);

    boolean existsByCity_IdAndNameIgnoreCase(Long cityId, String name);
}