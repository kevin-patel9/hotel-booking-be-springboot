package com.kevin.hotelbooking.controller;

import com.kevin.hotelbooking.dtos.BookingCreateDto;
import com.kevin.hotelbooking.dtos.RoomCreateRequestDto;
import com.kevin.hotelbooking.dtos.RoomNumberRequestDto;
import com.kevin.hotelbooking.entities.BookedRoom;
import com.kevin.hotelbooking.entities.Room;
import com.kevin.hotelbooking.mapper.RoomMapper;
import com.kevin.hotelbooking.mapper.RoomNumberMapper;
import com.kevin.hotelbooking.repository.BookedRoomRepository;
import com.kevin.hotelbooking.repository.HotelRepository;
import com.kevin.hotelbooking.repository.RoomNumberRepository;
import com.kevin.hotelbooking.repository.RoomRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;
    private final RoomNumberMapper roomNumberMapper;
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

        var roomNumberDetail = roomNumberMapper.toRoomDto(request);
        roomNumberDetail.setRoom(room);

        roomNumberRepository.save(roomNumberDetail);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("message", "Room number created")
        );
    }

    @PostMapping("/createBooking")
    public ResponseEntity<?> createdBookingForRoom(
            @RequestBody BookingCreateDto request
            ) {

        var roomNumberData = roomNumberRepository.findById(request.getRoomNumber()).orElse(null);

        if (roomNumberData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Room number not found")
            );
        }

        request.getDates().forEach(date -> {
            BookedRoom bookedRoom = new BookedRoom();

            bookedRoom.setRoomNumber(roomNumberData);
            bookedRoom.setBookedDate(date);

            bookedRoomRepository.save(bookedRoom);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("message", "Room number created")
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
}
