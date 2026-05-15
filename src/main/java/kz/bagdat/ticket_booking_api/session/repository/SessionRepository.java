package kz.bagdat.ticket_booking_api.session.repository;

import kz.bagdat.ticket_booking_api.session.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    List<SessionEntity> findAllByEvent_Id(Long eventId);

    List<SessionEntity> findAllByHall_Id(Long hallId);

    List<SessionEntity> findAllByStartTimeAfter(LocalDateTime startTime);
}