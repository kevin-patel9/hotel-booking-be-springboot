package com.kevin.hotelbooking.repository;

import com.kevin.hotelbooking.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {

    List<Room> findByHotelId(UUID hotel_id);
}