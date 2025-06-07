package com.kevin.hotelbooking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RoomWithBookingsDto {
    private UUID id;
    private String title;
    private BigDecimal price;
    private int maxPeople;
    private String desc;
    private List<RoomNumberWithBookingsDto> roomNumbers;
}
