package com.kevin.hotelbooking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRegisterResponseDto {
    private String username;
    private String email;
    private Byte is_admin;
}
