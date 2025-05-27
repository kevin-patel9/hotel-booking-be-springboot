package com.kevin.hotelbooking.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelListDto {
    private String type;
    private String city;
    private String address;
    private String distance;
    private BigDecimal price = BigDecimal.ZERO;
    private Byte featured;
    private String desc;
    private Byte rating;
}
