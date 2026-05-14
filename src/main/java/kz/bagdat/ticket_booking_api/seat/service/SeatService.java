package kz.bagdat.ticket_booking_api.seat.service;

import kz.bagdat.ticket_booking_api.common.exception.HallNotFoundException;
import kz.bagdat.ticket_booking_api.common.exception.SeatAlreadyExistsException;
import kz.bagdat.ticket_booking_api.common.exception.SeatNotFoundException;
import kz.bagdat.ticket_booking_api.hall.entity.HallEntity;
import kz.bagdat.ticket_booking_api.hall.repository.HallRepository;
import kz.bagdat.ticket_booking_api.seat.dto.CreateSeatRequest;
import kz.bagdat.ticket_booking_api.seat.dto.SeatResponse;
import kz.bagdat.ticket_booking_api.seat.dto.UpdateSeatRequest;
import kz.bagdat.ticket_booking_api.seat.entity.SeatEntity;
import kz.bagdat.ticket_booking_api.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    @Transactional(readOnly = true)
    public List<SeatResponse> getAllSeats(Long hallId) {
        List<SeatEntity> seats = hallId == null
                ? seatRepository.findAll()
                : seatRepository.findAllByHall_Id(hallId);

        return seats.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SeatResponse getSeatById(Long id) {
        SeatEntity seat = seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));

        return toResponse(seat);
    }

    @Transactional
    public SeatResponse createSeat(CreateSeatRequest request) {
        Long hallId = request.hallId();
        Integer rowNumber = request.rowNumber();
        Integer seatNumber = request.seatNumber();
        String seatType = request.seatType().trim().toUpperCase();

        HallEntity hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));

        if (seatRepository.existsByHall_IdAndRowNumberAndSeatNumber(hallId, rowNumber, seatNumber)) {
            throw new SeatAlreadyExistsException("Seat with this row and number already exists in this hall");
        }

        SeatEntity seat = new SeatEntity();
        seat.setHall(hall);
        seat.setRowNumber(rowNumber);
        seat.setSeatNumber(seatNumber);
        seat.setSeatType(seatType);
        seat.setCreatedAt(LocalDateTime.now());

        SeatEntity savedSeat = seatRepository.save(seat);

        return toResponse(savedSeat);
    }

    @Transactional
    public SeatResponse updateSeat(Long id, UpdateSeatRequest request) {
        SeatEntity seat = seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));

        Long newHallId = request.hallId();
        Integer newRowNumber = request.rowNumber();
        Integer newSeatNumber = request.seatNumber();
        String newSeatType = request.seatType().trim().toUpperCase();

        HallEntity hall = hallRepository.findById(newHallId)
                .orElseThrow(() -> new HallNotFoundException("Hall not found"));

        seatRepository.findByHall_IdAndRowNumberAndSeatNumber(newHallId, newRowNumber, newSeatNumber)
                .filter(existingSeat -> !existingSeat.getId().equals(id))
                .ifPresent(existingSeat -> {
                    throw new SeatAlreadyExistsException("Seat with this row and number already exists in this hall");
                });

        seat.setHall(hall);
        seat.setRowNumber(newRowNumber);
        seat.setSeatNumber(newSeatNumber);
        seat.setSeatType(newSeatType);

        SeatEntity savedSeat = seatRepository.save(seat);

        return toResponse(savedSeat);
    }

    private SeatResponse toResponse(SeatEntity seat) {
        return new SeatResponse(
                seat.getId(),
                seat.getHall().getId(),
                seat.getHall().getName(),
                seat.getRowNumber(),
                seat.getSeatNumber(),
                seat.getSeatType()
        );
    }
}