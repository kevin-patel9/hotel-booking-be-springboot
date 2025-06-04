package com.kevin.hotelbooking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class UsersDetailResponseDto {
    private Integer id;
    private String username;
    private String email;
    private String city;
    private String country;
    private String phone;
}
