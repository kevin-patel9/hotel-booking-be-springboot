package com.kevin.hotelbooking.controller;

import com.kevin.hotelbooking.dtos.*;
import com.kevin.hotelbooking.entities.BookedRoom;
import com.kevin.hotelbooking.entities.Room;
import com.kevin.hotelbooking.entities.RoomNumber;
import com.kevin.hotelbooking.exception.RoomNotFoundException;
import com.kevin.hotelbooking.mapper.RoomMapper;
import com.kevin.hotelbooking.mapper.RoomNumberMapper;
import com.kevin.hotelbooking.repository.BookedRoomRepository;
import com.kevin.hotelbooking.repository.HotelRepository;
import com.kevin.hotelbooking.repository.RoomNumberRepository;
import com.kevin.hotelbooking.repository.RoomRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;
    private final RoomNumberRepository roomNumberRepository;
    private final BookedRoomRepository bookedRoomRepository;

    @GetMapping
    public ResponseEntity<List<Room>> findAll() {
        var allRoom = roomRepository.findAll();
        return ResponseEntity.ok(allRoom);
    }

    @PostMapping("/createRoom")
    public ResponseEntity<?> addNewRoom(
            @Valid @RequestBody RoomCreateRequestDto request
    ) {
        var hotel = hotelRepository.findById(request.getHotelId()).orElse(null);

        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Hotel not found")
            );
        }

        var newRoom = roomMapper.toRoom(request);
        newRoom.setHotel(hotel);

        var finalRoom = roomRepository.save(newRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(finalRoom);
    }

    @PostMapping("/createRoomNumber")
    public ResponseEntity<?> createNewRoomNumber(
            @Valid @RequestBody RoomNumberRequestDto request
    ) {
        var room = roomRepository.findById(request.getRoomId()).orElse(null);

        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Room not found")
            );
        }

        request.getRoomNumbers().forEach(roomNumber -> {
            var newRoomNumberDetail = new RoomNumber();
            newRoomNumberDetail.setNumber(roomNumber);
            newRoomNumberDetail.setRoom(room);

            roomNumberRepository.save(newRoomNumberDetail);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("message", "Room number created")
        );
    }

    @PostMapping("/availability")
    public ResponseEntity<?> createBookingForRoom(@RequestBody BookingCreateDto request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        if (startDate == null || endDate == null) {
            return ResponseEntity.badRequest().body(new MessageDto("Start date and end date are required"));
        }

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(new MessageDto("Start date must be before end date"));
        }

        var room = roomRepository.findById(request.getRoomId()).orElse(null);
        if (room == null) {
            throw new RoomNotFoundException();
        }

        List<BookedRoom> newBookings = new ArrayList<>();

        for (Integer roomNumberId : request.getRoomNumber()) {
            var roomNumber = roomNumberRepository.findByNumberAndRoom(roomNumberId, room).orElse(null);
            if (roomNumber == null) continue;

            List<LocalDate> dateRange = startDate.datesUntil(endDate.plusDays(1)).toList();
            boolean isUnavailable = bookedRoomRepository.existsByRoomNumberIdAndBookedDateIn(roomNumberId, dateRange);
            if (isUnavailable) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        Map.of("message", "Room number " + roomNumberId + " is not available for the selected dates")
                );
            }

            for (LocalDate date : dateRange) {
                BookedRoom bookedRoom = new BookedRoom();
                bookedRoom.setRoomNumber(roomNumber);
                bookedRoom.setBookedDate(date);
                newBookings.add(bookedRoom);
            }
        }

        bookedRoomRepository.saveAll(newBookings);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("message", "Room bookings created")
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(
        @PathVariable UUID id
    ) {

        if (roomRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Room not found")
            );
        }

        roomRepository.deleteById(id);

        return ResponseEntity.ok(
                Map.of("message", "Room successfully deleted")
        );
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoomNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("message", "Room not found")
        );
    }
}
