package com.kevin.hotelbooking.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class HotelUpdateRequestDto {
    private UUID hotelId;
    private String name;
    private String address;
    private String type;
    private String city;
    private BigDecimal price;
    private String desc;
    private String distance;
    private Byte rating;
}
