package com.kevin.hotelbooking.repository;

import com.kevin.hotelbooking.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, String> {
    int countByCity(String city);
    int countByType(String type);

    List<Hotel> findByPriceBetween(BigDecimal priceAfter, BigDecimal priceBefore);
}
