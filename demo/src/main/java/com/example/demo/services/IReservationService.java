package com.example.demo.services;

import com.example.demo.dtos.CreateReservationDTO;
import com.example.demo.dtos.GetReservationDTO;
import com.example.demo.models.Reservation;

import java.util.List;
import java.util.Optional;

public interface IReservationService {
    Reservation createReservation(CreateReservationDTO reservation);
    Optional<Reservation> cancelReservation(Long reservationId);
    List<GetReservationDTO> findReservations();
}
