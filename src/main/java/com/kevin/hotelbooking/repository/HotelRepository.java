package com.kevin.hotelbooking.repository;

import com.kevin.hotelbooking.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {
    int countByCity(String city);
    int countByType(String type);

    List<Hotel> findByPriceBetweenAndCity(BigDecimal priceAfter, BigDecimal priceBefore, String city);
}
