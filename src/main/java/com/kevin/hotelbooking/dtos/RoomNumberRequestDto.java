package com.kevin.hotelbooking.dtos;

import com.kevin.hotelbooking.entities.Room;
import lombok.Data;

import java.util.UUID;

@Data
public class RoomNumberRequestDto {
    private UUID roomId;
    private Integer roomNumber;
}
