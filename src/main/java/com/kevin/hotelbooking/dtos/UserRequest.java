package com.kevin.hotelbooking.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Country is required")
    private String country;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "PhoneNumber is required")
    private String phone;

    @NotNull(message = "Password is required")
    @Min(value = 4, message = "Password should be greater than 4")
    private String password;
}
