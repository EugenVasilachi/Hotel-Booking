package com.example.demo.services;

import com.example.demo.models.Room;

import java.util.List;
import java.util.Optional;


public interface IRoomService {
    List<Room> findRooms();
    Optional<Room> findRoomById(Long roomId);
    Optional<List<Room>> findRoomsByHotelId(Long hotelId);
    Room saveRoom(Room room);
    Room updateRoom(Long id, Room room);
    void deleteRoom(Long roomId);
    Optional<Room> makeReservation(Long roomId);
}
