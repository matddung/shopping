package com.studyjun.shopping.controller;

import com.studyjun.shopping.dto.request.LoginRequest;
import com.studyjun.shopping.dto.request.UserInfoRequest;
import com.studyjun.shopping.service.UserService;
import com.studyjun.shopping.util.CurrentUser;
import com.studyjun.shopping.util.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Answer", description = "User interaction")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "로그인", description = "로그인합니다.")
    @PostMapping("/login")
    private ResponseEntity<?> login(
            @Parameter(description = "loginRequest를 작성해주세요.", required = true) @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @PostMapping("/logout")
    private ResponseEntity<?> logout(
            @Parameter(description = "refreshToken를 입력해주세요.", required = true) @RequestParam String refreshToken) {
        return userService.logout(refreshToken);
    }

    @Operation(summary = "토큰 갱신", description = "신규 토큰을 갱신합니다.")
    @PostMapping("/refresh")
    private ResponseEntity<?> refresh(
            @Parameter(description = "refreshToken를 입력해주세요.", required = true) @RequestParam String refreshToken) {
        return userService.refresh(refreshToken);
    }

    @Operation(summary = "정보 수정", description = "회원의 정보를 수정합니다.")
    @PatchMapping("/modify")
    private ResponseEntity<?> modifyUserInfo(
            @Parameter(description = "loginRequest를 작성해주세요.", required = true) @RequestBody UserInfoRequest userInfoRequest,
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
            ) {
        return userService.modifyUserInfo(userInfoRequest, userPrincipal);
    }
}
