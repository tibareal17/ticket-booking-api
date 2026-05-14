package kz.bagdat.ticket_booking_api.seat.entity;

import jakarta.persistence.*;
import kz.bagdat.ticket_booking_api.hall.entity.HallEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private HallEntity hall;

    @Column(name = "row_number", nullable = false)
    private Integer rowNumber;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Column(name = "seat_type", nullable = false)
    private String seatType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
