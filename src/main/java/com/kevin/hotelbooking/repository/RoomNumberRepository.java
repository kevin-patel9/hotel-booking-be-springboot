package com.kevin.hotelbooking.repository;

import com.kevin.hotelbooking.entities.Room;
import com.kevin.hotelbooking.entities.RoomNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoomNumberRepository extends JpaRepository<RoomNumber, Integer> {
    Optional<RoomNumber> findByNumberAndRoom(Integer number, Room room);
}
