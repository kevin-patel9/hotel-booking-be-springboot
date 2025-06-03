package com.kevin.hotelbooking.repository;

import com.kevin.hotelbooking.entities.RoomNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomNumberRepository extends JpaRepository<RoomNumber, Integer> {
}