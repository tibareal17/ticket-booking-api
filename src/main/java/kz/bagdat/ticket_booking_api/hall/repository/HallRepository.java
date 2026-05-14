package kz.bagdat.ticket_booking_api.hall.repository;

import kz.bagdat.ticket_booking_api.hall.entity.HallEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HallRepository extends JpaRepository<HallEntity, Long> {

    List<HallEntity> findAllByVenue_Id(Long venueId);

    Optional<HallEntity> findByVenue_IdAndNameIgnoreCase(Long venueId, String name);

    boolean existsByVenue_IdAndNameIgnoreCase(Long venueId, String name);
}