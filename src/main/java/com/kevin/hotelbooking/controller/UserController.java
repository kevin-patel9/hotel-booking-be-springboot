package com.kevin.hotelbooking.controller;

import com.kevin.hotelbooking.dtos.UsersDetailResponseDto;
import com.kevin.hotelbooking.mapper.UserMapper;
import com.kevin.hotelbooking.repository.UserRepository;
import com.kevin.hotelbooking.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        var users = userRepository.findAll();

        if (users.isEmpty())
            return ResponseEntity.ok(users);

        List<UsersDetailResponseDto> userList = users.stream().map(
                userMapper::toUsersDetailResponseDto
        ).toList();

        return ResponseEntity.ok(userList);
    }
}
