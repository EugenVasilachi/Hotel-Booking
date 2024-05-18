package com.example.demo.services.impl;

import com.example.demo.models.Room;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.services.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> findRoomById(Long roomId) {
        return roomRepository.findById(roomId);
    }
    public Optional<List<Room>> findRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId)
                .map(rooms -> rooms.stream()
                        .filter(Room::isAvailable)
                        .collect(Collectors.toList()));
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    public Optional<Room> makeReservation(Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setAvailable(false);
            roomRepository.save(room);
        }
        return optionalRoom;
    }

    public Room updateRoom(Long roomId, Room room) {
        Optional<Room> updatedRoom = roomRepository.findById(roomId);
        if (updatedRoom.isEmpty()) {
            return null;
        }
        updatedRoom.get().setRoomNumber(room.getRoomNumber());
        updatedRoom.get().setType(room.getType());
        updatedRoom.get().setPrice(room.getPrice());
        updatedRoom.get().setAvailable(room.isAvailable());
        return roomRepository.save(updatedRoom.get());
    }
}
