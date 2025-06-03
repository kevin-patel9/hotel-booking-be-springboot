package com.kevin.hotelbooking.dtos;

import lombok.Data;

@Data
public class UserDetailUpdateDto {
    private String username;
    private String country;
    private String city;
    private String phone;
    private String email;
}
