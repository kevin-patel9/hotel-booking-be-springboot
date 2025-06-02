package com.kevin.hotelbooking.repository;

import com.kevin.hotelbooking.entities.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Integer> {
}