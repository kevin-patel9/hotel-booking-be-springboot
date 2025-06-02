package com.kevin.hotelbooking.dtos;

import com.kevin.hotelbooking.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RoomBookedCreateDto {
    private Integer roomNumber;
    private LocalDate date;
    private String roomId;
}
