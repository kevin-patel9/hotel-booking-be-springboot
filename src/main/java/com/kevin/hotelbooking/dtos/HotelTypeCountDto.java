package com.kevin.hotelbooking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelTypeCountDto {
    private String type;
    private int count;
}
