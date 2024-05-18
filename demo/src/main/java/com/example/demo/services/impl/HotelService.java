package com.example.demo.services.impl;

import com.example.demo.models.Hotel;
import com.example.demo.models.Room;
import com.example.demo.repositories.HotelRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.services.IHotelService;
import com.example.demo.utils.GeoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public List<Hotel> findHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> findHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId);
    }

    public Hotel saveHotel(Hotel hotel) {
        Hotel savedHotel = hotelRepository.save(hotel);

        for (Room room : hotel.getRooms()) {
            room.setHotel(savedHotel);
        }

        roomRepository.saveAll(hotel.getRooms());

        return savedHotel;
    }

    public void deleteHotel(Long roomId) {
        hotelRepository.deleteById(roomId);
    }

    public Optional<Hotel> associateHotelWithRoom(Long hotelId, Long roomId) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalHotel.isPresent() && optionalRoom.isPresent()) {
            Hotel hotel = optionalHotel.get();
            Room room = optionalRoom.get();

            room.setHotel(hotel);
            hotel.getRooms().add(room);

            hotelRepository.save(hotel);
            roomRepository.save(room);
        }

        return optionalHotel;
    }

    public List<Hotel> findHotelsNearby(double radius, double latitude, double longitude) {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<Hotel> nearbyHotels = new ArrayList<>();

        for (Hotel hotel : allHotels) {
            double distance = GeoUtils.calculateDistanceBetweenTwoPositions(
                    latitude, longitude,
                    hotel.getLatitude(), hotel.getLongitude()
            );
            if (distance <= radius) {
                nearbyHotels.add(hotel);
            }
        }

        return nearbyHotels;
    }

    public Hotel updateHotel(Long hotelId, Hotel hotel) {
        Optional<Hotel> updatedHotel = hotelRepository.findById(hotelId);
        if (updatedHotel.isEmpty()) {
            return null;
        }
        updatedHotel.get().setName(hotel.getName());
        updatedHotel.get().setLatitude(hotel.getLatitude());
        updatedHotel.get().setLongitude(hotel.getLongitude());
        return hotelRepository.save(updatedHotel.get());
    }
}
