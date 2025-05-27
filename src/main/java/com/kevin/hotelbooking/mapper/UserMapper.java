package com.kevin.hotelbooking.mapper;

import com.kevin.hotelbooking.dtos.UserRequest;
import com.kevin.hotelbooking.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserRequest(UserRequest userRequest);
}
