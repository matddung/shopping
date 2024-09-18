package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.LoginRequest;
import com.studyjun.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/logout")
    private ResponseEntity<?> logout(String refreshToken) {
        return userService.logout(refreshToken);
    }

    @PostMapping("/refresh")
    private ResponseEntity<?> refresh(String refreshToken) {
        return userService.refresh(refreshToken);
    }
}
