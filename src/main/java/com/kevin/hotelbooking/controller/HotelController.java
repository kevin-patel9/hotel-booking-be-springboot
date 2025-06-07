package com.kevin.hotelbooking.controller;

import com.kevin.hotelbooking.dtos.*;
import com.kevin.hotelbooking.entities.BookedRoom;
import com.kevin.hotelbooking.entities.Hotel;
import com.kevin.hotelbooking.mapper.HotelMapper;
import com.kevin.hotelbooking.repository.BookedRoomRepository;
import com.kevin.hotelbooking.repository.HotelRepository;
import com.kevin.hotelbooking.repository.RoomRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final HotelMapper hotelMapper;
    private final BookedRoomRepository bookedRoomRepository;

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

    @GetMapping("/filterHotel")
    public ResponseEntity<?> getHotelDetails(
            @RequestParam(defaultValue = "1") BigDecimal min,
            @RequestParam(defaultValue = "999") BigDecimal max,
            @RequestParam String city
    ) {
        List<Hotel> hotels = hotelRepository.findByPriceBetweenAndCity(min, max, city);

        if (hotels.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "No hotel available for the following city")
            );

        return ResponseEntity.ok(hotels);
    }

    @GetMapping
    public ResponseEntity<?> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        if (hotels.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(List.of());

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
                            "message", "hotel not found"
                    )
            );
        }

        return ResponseEntity.ok(hotel);
    }

    @GetMapping("/room/{hotelId}")
    public ResponseEntity<?> getAllRoomsByHotelId(@PathVariable UUID hotelId) {
        var rooms = roomRepository.findByHotelId(hotelId);

        List<RoomWithBookingsDto> response = rooms.stream().map(room -> {
            List<RoomNumberWithBookingsDto> roomNumberDtos = room.getRoomNumbers().stream().map(roomNumber -> {
                List<LocalDate> bookedDates = bookedRoomRepository
                        .findAllByRoomNumberId(roomNumber.getId())
                        .stream()
                        .map(BookedRoom::getBookedDate)
                        .collect(Collectors.toList());

                return new RoomNumberWithBookingsDto(
                        roomNumber.getId(),
                        roomNumber.getNumber(),
                        bookedDates
                );
            }).collect(Collectors.toList());

            return new RoomWithBookingsDto(
                    room.getId(),
                    room.getTitle(),
                    room.getPrice(),
                    room.getMaxPeople(),
                    room.getDesc(),
                    roomNumberDtos
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/updateHotel")
    public ResponseEntity<?> updateHotelDetails(
            @RequestBody HotelUpdateRequestDto request
    ) {

        var hotelToUpdate = hotelRepository.findById(request.getHotelId()).orElse(null);

        if (hotelToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "hotel not found")
            );
        }

        var hotel = hotelMapper.updateHotelFromDto(request, hotelToUpdate);

        hotelRepository.save(hotel);

        return ResponseEntity.ok(
                Map.of("message", "Hotel updated")
        );
    }

    @DeleteMapping("/deleteHotel/{hotelId}")
    public ResponseEntity<Map<String, String>> deleteHotel(
            @PathVariable UUID hotelId
    ){
        var hotel = hotelRepository.findById(hotelId).orElse(null);

        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "hotel not found")
            );
        }

        hotelRepository.delete(hotel);

        return ResponseEntity.ok(Map.of("message", "Hotel deleted"));
    }
}
