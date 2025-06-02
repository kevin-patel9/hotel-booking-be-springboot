package com.kevin.hotelbooking.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class BookingCreateDto {
    private Integer roomNumber;
    private List<LocalDate> dates;
}
