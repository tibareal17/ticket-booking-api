package kz.bagdat.ticket_booking_api.event.repository;

import kz.bagdat.ticket_booking_api.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllByCategory_Id(Long categoryId);

    boolean existsByTitleIgnoreCase(String title);
}