package kz.bagdat.ticket_booking_api.hall.entity;

import jakarta.persistence.*;
import kz.bagdat.ticket_booking_api.venue.entity.VenueEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "halls")
@Getter
@Setter
@NoArgsConstructor
public class HallEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private VenueEntity venue;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}