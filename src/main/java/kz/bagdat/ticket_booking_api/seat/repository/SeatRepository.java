package kz.bagdat.ticket_booking_api.seat.repository;

import kz.bagdat.ticket_booking_api.seat.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findAllByHall_Id(Long hallId);

    Optional<SeatEntity> findByHall_IdAndRowNumberAndSeatNumber(
            Long hallId,
            Integer rowNumber,
            Integer seatNumber
    );

    boolean existsByHall_IdAndRowNumberAndSeatNumber(
            Long hallId,
            Integer rowNumber,
            Integer seatNumber
    );
}
