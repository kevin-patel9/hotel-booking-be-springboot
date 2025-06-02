package com.kevin.hotelbooking.controller;

import com.kevin.hotelbooking.dtos.HotelCreateRequestDto;
import com.kevin.hotelbooking.dtos.HotelTypeCountDto;
import com.kevin.hotelbooking.entities.Hotel;
import com.kevin.hotelbooking.mapper.HotelMapper;
import com.kevin.hotelbooking.repository.HotelRepository;
import com.kevin.hotelbooking.repository.RoomRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final HotelMapper hotelMapper;

    @GetMapping("/cityCount")
    public List<Integer> getCityCount(@RequestParam(required = false, name = "cities") String cities) {
        if (cities == null || cities.isBlank())
            return List.of();

        return Arrays.stream(cities.split(","))
                .map(String::trim)
                .map(hotelRepository::countByCity)
                .toList();
    }

    @GetMapping("/typeCount")
    public ResponseEntity<List<HotelTypeCountDto>> getHotelTypeCount() {
        List<String> hotelTypes = List.of("hotel", "apartment", "villa", "resort", "cabin");

        List<HotelTypeCountDto> response = hotelTypes.stream()
                .map(type -> new HotelTypeCountDto(type, hotelRepository.countByType(type)))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getHotelDetails(
            @RequestParam(defaultValue = "1") BigDecimal min,
            @RequestParam(defaultValue = "999") BigDecimal max,
            @RequestParam String city
    ) {
        List<Hotel> hotels = hotelRepository.findByPriceBetweenAndCity(min, max, city);

        if (hotels.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "No hotel available.")
            );

        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/createHotel")
    public ResponseEntity<?> createHotel(
            @Valid @RequestBody HotelCreateRequestDto request
    ){
        var hotel = hotelMapper.createHotel(request);
        hotel.setPhoto(null);
        hotel.setRooms(null);

        hotelRepository.save(hotel);

        return ResponseEntity.ok(hotel);
    }

    @GetMapping("/find/{hotelId}")
    public ResponseEntity<?> getSingleHotel(
            @PathVariable UUID hotelId
    ) {

        var hotel = hotelRepository.findById(hotelId);

        if (hotel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "message", "hotel not found",
                            "status", "false"
                    )
            );
        }

        return ResponseEntity.ok(hotel);
    }

    @GetMapping("/room/{hotelId}")
    public ResponseEntity<?> getAllRoomsByHotelId(
            @PathVariable UUID hotelId
    ) {

        var allRoom = roomRepository.findByHotelId(hotelId);

        return ResponseEntity.ok(allRoom);
    }
}
