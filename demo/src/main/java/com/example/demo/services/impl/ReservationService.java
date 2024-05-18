package com.example.demo.services.impl;

import com.example.demo.dtos.CreateReservationDTO;
import com.example.demo.dtos.GetReservationDTO;
import com.example.demo.models.Reservation;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public Reservation createReservation(CreateReservationDTO reservationDTO) {

        Reservation reservation = Reservation.builder()
                .room(roomRepository.findById(reservationDTO.getRoomId()).orElseThrow(() -> new RuntimeException("Hotel not found")))
                .checkIn(reservationDTO.getCheckIn())
                .checkOut(reservationDTO.getCheckOut())
                .createdAt(LocalDateTime.now())
                .build();

        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> cancelReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent() && reservation.get().getCheckIn().isAfter(LocalDateTime.now().plusHours(2))) {
            reservationRepository.delete(reservation.get());
            return reservation;
        }
        return Optional.empty();
    }

    public List<GetReservationDTO> findReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private GetReservationDTO convertToDTO(Reservation reservation) {
        return GetReservationDTO.builder()
                .id(reservation.getId())
                .roomNumber(reservation.getRoom().getRoomNumber())
                .roomType(reservation.getRoom().getType())
                .hotelName(reservation.getRoom().getHotel().getName())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
