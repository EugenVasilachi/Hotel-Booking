package com.example.demo.services;

import com.example.demo.models.Hotel;

import java.util.List;
import java.util.Optional;

public interface IHotelService {

    List<Hotel> findHotels();
    Optional<Hotel> findHotelById(Long hotelId);
    Hotel saveHotel(Hotel hotel);
    Hotel updateHotel(Long hotelId, Hotel hotel);
    void deleteHotel(Long hotelId);
    Optional<Hotel> associateHotelWithRoom(Long hotelId, Long roomId);
    List<Hotel> findHotelsNearby(double radius, double latitude, double longitude);
}
