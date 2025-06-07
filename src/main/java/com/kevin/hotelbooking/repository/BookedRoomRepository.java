package com.kevin.hotelbooking.repository;

import com.kevin.hotelbooking.entities.BookedRoom;
import com.kevin.hotelbooking.entities.RoomNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Integer> {
    boolean existsByRoomNumberIdAndBookedDateIn(int roomNumberId, Collection<LocalDate> bookedDates);
    List<BookedRoom> findAllByRoomNumberId(Integer roomNumberId);
}