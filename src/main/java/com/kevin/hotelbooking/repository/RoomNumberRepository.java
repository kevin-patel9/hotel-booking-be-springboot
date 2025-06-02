package com.kevin.hotelbooking.repository;

import com.kevin.hotelbooking.entities.RoomNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomNumberRepository extends JpaRepository<RoomNumber, Integer> {
}