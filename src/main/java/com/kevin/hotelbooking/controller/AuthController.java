package com.kevin.hotelbooking.controller;

import com.kevin.hotelbooking.dtos.UserLoginRequest;
import com.kevin.hotelbooking.dtos.UserRequest;
import com.kevin.hotelbooking.entities.User;
import com.kevin.hotelbooking.mapper.UserMapper;
import com.kevin.hotelbooking.repository.UserRepository;
import com.kevin.hotelbooking.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody UserRequest request
    ) {
        var userDetail = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (userDetail != null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "User already exists")
            );
        }

        var user = userMapper.toUserRequest(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(
                Map.of("message", "User registered successfully")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (
        @Valid @RequestBody UserLoginRequest request,
        HttpServletResponse response
    ){

       authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
       );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var accessToken = jwtService.generateToken(user);
        var cookie = new Cookie("token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logged successfully"));

    }
}
