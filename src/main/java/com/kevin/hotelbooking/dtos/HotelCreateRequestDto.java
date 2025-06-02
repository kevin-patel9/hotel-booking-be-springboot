package com.kevin.hotelbooking.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelCreateRequestDto {
    private String name;

    private String type;
    private String desc;

    @NotNull
    private String city;

    @NotNull
    private String address;

    @NotNull
    private String distance;

    @NotNull
    private Byte rating;

    @NotNull
    private BigDecimal price;
}
