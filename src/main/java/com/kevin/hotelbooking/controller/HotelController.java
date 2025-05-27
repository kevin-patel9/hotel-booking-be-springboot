package com.kevin.hotelbooking.controller;

import com.kevin.hotelbooking.dtos.HotelTypeCountDto;
import com.kevin.hotelbooking.entities.Hotel;
import com.kevin.hotelbooking.mapper.HotelMapper;
import com.kevin.hotelbooking.repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @GetMapping("/cityCount")
    public List<Integer> getCityCount(@RequestParam(required = false, name = "cities") String cities) {
        if (cities == null || cities.isBlank())
            return List.of();

        return Arrays.stream(cities.split(","))
                .map(String::trim)
                .map(hotelRepository::countByCity)
                .toList();
    }

    @GetMapping("/typeCount")
    public ResponseEntity<List<HotelTypeCountDto>> getHotelTypeCount() {
        List<String> hotelTypes = List.of("hotel", "apartment", "villa", "resort", "cabin");

        List<HotelTypeCountDto> response = hotelTypes.stream()
                .map(type -> new HotelTypeCountDto(type, hotelRepository.countByType(type)))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getHotelDetails(
            @RequestParam(defaultValue = "1") BigDecimal min,
            @RequestParam(defaultValue = "999") BigDecimal max
    ) {
        List<Hotel> hotels = hotelRepository.findByPriceBetween(min, max);
        return ResponseEntity.ok(hotels);
    }
}
