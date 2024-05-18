package com.example.demo.controllers;

import com.example.demo.models.Hotel;
import com.example.demo.services.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/hotels")
public class HotelController {

    private IHotelService hotelService;

    @Autowired
    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.findHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId) {
        return hotelService.findHotelById(hotelId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nearby/{userRadius}/{userLatitude}/{userLongitude}")
    public ResponseEntity<List<Hotel>> getHotelsNearby(@PathVariable double userRadius,
                                                       @PathVariable double userLatitude,
                                                       @PathVariable double userLongitude) {
        List<Hotel> nearHotels = hotelService.findHotelsNearby(userRadius, userLatitude, userLongitude);
        return ResponseEntity.ok(nearHotels);
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @PutMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<Hotel> associateHotelWithRoom(@PathVariable Long hotelId, @PathVariable Long roomId) {
        return hotelService.associateHotelWithRoom(hotelId, roomId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long hotelId, @RequestBody Hotel updatedHotel) {
        Hotel updated = hotelService.updateHotel(hotelId, updatedHotel);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }
}
