package com.kevin.hotelbooking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class RoomNumberWithBookingsDto {
    private Integer id;
    private Integer number;
    private List<LocalDate> bookedDates;
}
