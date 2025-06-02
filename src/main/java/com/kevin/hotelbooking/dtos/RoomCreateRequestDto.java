package com.kevin.hotelbooking.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RoomCreateRequestDto {
    private UUID hotelId;
    private String title;
    private Byte maxPeople;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String desc;
}
