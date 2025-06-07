package com.kevin.hotelbooking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class BookedRoomRequestDto {
    private String roomNumber;
    private LocalDate date;
}
